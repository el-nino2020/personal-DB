package service;

import com.google.common.base.Preconditions;
import common.Param;
import dao.TableInfoDAO;
import domain.TableInfo;
import utils.Utility;

import java.io.File;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * 负责数据库的备份和已有表的查询，也许还要负责新建表？
 */
public class DBService {
    private static final String DUMP_DESTINATION = Param.DUMP_DESTINATION;

    private AccountService accountService;
    private TableInfoDAO tableInfoDAO = new TableInfoDAO();

    public DBService(AccountService accountService) {
        this.accountService = accountService;
    }


    /**
     * 从meta_table中获取所有表的信息
     */
    public List<TableInfo> getAllTableInfo() {
        Preconditions.checkState(accountService.getLoginStatus(), "数据库账户未登录");

        List<TableInfo> ans = tableInfoDAO.queryMultiRow(accountService.getConnection(),
                "select * from meta_table;",
                TableInfo.class);
        return ans;
    }


    public boolean tableExists(String tableName) {
        Preconditions.checkState(accountService.getLoginStatus(), "数据库账户未登录");
        Preconditions.checkNotNull(tableName);

        List<String> tables = getAllTableNames();
        return tables.contains(tableName);
    }

    public List<String> getAllTableNames() {
        Preconditions.checkState(accountService.getLoginStatus(), "数据库账户未登录");
        Connection connection = accountService.getConnection();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ArrayList<String> ans = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement("SHOW TABLES;");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String tableName = resultSet.getString(1);
                ans.add(tableName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return ans;
    }


    /**
     * 找到tableName表的AUTO_INCREMENT的下一个值。参考：
     * https://stackoverflow.com/questions/15821532/get-current-auto-increment-value-for-any-table
     *
     * @param tableName 要查询的表名
     * @return value(AUTO_INCREMENT) + ""，把这个值以字符串形式返回
     */
    public String getAUTOINCREMENTValue(String tableName) {
        Preconditions.checkState(accountService.getLoginStatus(), "数据库账户未登录");
        Preconditions.checkNotNull(tableName);
        Preconditions.checkArgument(tableExists(tableName));//保险起见


        Connection connection = accountService.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String ans = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SHOW TABLE STATUS FROM cloud_backup WHERE `name` LIKE ? ");
            preparedStatement.setString(1, tableName);

            resultSet = preparedStatement.executeQuery();
            //resultSet肯定只有一行结果，但一开始游标在第一个结果之前，所以需要调用next()方法
            resultSet.next();
            ResultSetMetaData metaData = resultSet.getMetaData();//可以看出metaData也是基于游标的

            for (int i = 0; i < metaData.getColumnCount(); i++) {
                //注意到: resultSet中字段的顺序并不是固定的，应该根据列名选出AUTO_INCREMENT的值
                if ("AUTO_INCREMENT".equals(metaData.getColumnName(i + 1))) {
                    ans = resultSet.getString(i + 1);
                    break;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return ans;
    }


    /**
     * 备份cloud_backup数据库至DUMP_DESTINATION下，
     * 使用当前用户的密码压缩备份文件，然后删除备份文件。
     * 备份的命令参考：https://www.sqlshack.com/how-to-backup-and-restore-mysql-databases-using-the-mysqldump-command/
     * 写这个方法出现的问题参考：https://stackoverflow.com/questions/20820213/mysqldump-from-java-application
     */
    public void databaseDump() {
        Preconditions.checkState(accountService.getLoginStatus(), "数据库账户未登录");

        String backupFileName = AccountService.DATABASE +
                Utility.getFormattedTime(LocalDateTime.now());

        String mysqlDumpFilePath = DUMP_DESTINATION + backupFileName + ".sql";

        List<String> strings = Utility.runSystemCommand(null,
                Param.CMD_PATH, "/c",
                Param.MYSQLDUMP_PATH +
                        " -u " + AccountService.USER +
                        " -p" + accountService.getDBMSPassword() + " " +
                        AccountService.DATABASE + " > " +
                        mysqlDumpFilePath);


        Utility.assertion(new File(mysqlDumpFilePath).exists(),
                "生成备份文件失败");

        //生成压缩文件
        ArchiveService.compress(new File(mysqlDumpFilePath),
                backupFileName, accountService.getDBMSPassword());

        //删除原始备份文件
        Utility.runSystemCommand(null,
                Param.CMD_PATH, "/c",
                "del", mysqlDumpFilePath);

        String compressedDumpFilePath = DUMP_DESTINATION + backupFileName + ".rar";
        Utility.assertion(new File(compressedDumpFilePath).exists(),
                "压缩备份文件失败");

        System.out.println("数据库备份成功，本次备份生成的文件为: " +
                compressedDumpFilePath);
    }

    /**
     * 在数据库中创建一张新表，表的模式与template_table相同，并将新表的信息存入meta_table中。
     * 如果以上步骤都成功，则进行数据库备份
     */
    public void createNewTable(TableInfo tableInfo) {
        Preconditions.checkState(accountService.getLoginStatus(), "数据库账户未登录");
        Preconditions.checkNotNull(tableInfo);
        Preconditions.checkArgument(!tableExists(tableInfo.getTablename()),
                String.format("%s表已存在，无法再次创建", tableInfo.getTablename()));

        //TODO : 思考meta_table中的数据一致性问题, 即assert(meta_table.recordSize() == cloud_backup.tableSize() -2 )
        //先写入meta_table，再创建新表
        tableInfoDAO.update(accountService.getConnection(),
                "INSERT INTO meta_table(tablename, note) VALUES (?, ?)",
                tableInfo.getTablename(), tableInfo.getNote());


        try {
            Connection connection = accountService.getConnection();
            //如果在sql中使用 ? 代替表名，则实际生成的表名周围带有单引号，不是合法的SQL语句
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "CREATE TABLE " + tableInfo.getTablename() + " LIKE template_table;");
            preparedStatement.execute();
        } catch (SQLException e) {

        }

        Utility.assertion(tableExists(tableInfo.getTablename()),
                String.format("%s表创建失败", tableInfo.getTablename()));

        System.out.format("%s表创建成功\n", tableInfo.getTablename());

        databaseDump();
    }


}

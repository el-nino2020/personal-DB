package service;

import com.google.common.base.Preconditions;
import dao.TableInfoDAO;
import domain.TableInfo;
import utils.Utility;

import java.io.File;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 负责数据库的备份和已有表的查询，也许还要负责新建表？
 */
public class DBService {
    //要将数据库备份到哪个目录下，推荐设置为某一云盘的自动备份目录
    private static final String DUMP_DESTINATION = "C:\\Users\\Morgan\\Desktop\\mega同步\\";
    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private AccountService accountService;
    private TableInfoDAO tableInfoDAO = new TableInfoDAO();

    public DBService(AccountService accountService) {
        this.accountService = accountService;
    }


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
                if ("meta_table".equals(tableName))
                    continue;
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
                    "SHOW TABLE STATUS FROM cloud_backup WHERE `name` LIKE '?' ");
            preparedStatement.setString(1, tableName);

            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();

            //resultSet肯定只有一行结果
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
     */
    public void databaseDump() {
        Preconditions.checkState(accountService.getLoginStatus(), "数据库账户未登录");

        String backupFileName = AccountService.DATABASE +
                TIME_FORMATTER.format(LocalDateTime.now());

        mysqldump就是无法运行，不知道为啥。在View中输入"5"可以测试这个方法
        List<String> strings = Utility.runSystemCommand(null,
                "D:\\mysql5.7.19\\bin\\mysqldump.exe -u " + AccountService.USER +
                        " -p" + accountService.getDBMSPassword() + " " +
                        AccountService.DATABASE +
                        " > " +
                        DUMP_DESTINATION + backupFileName + ".sql");
        System.out.println(strings);

        //生成压缩文件
        new ArchiveService().compress(new File(DUMP_DESTINATION + backupFileName),
                backupFileName, accountService.getDBMSPassword());

        //删除原始备份文件
        Utility.runSystemCommand("C:\\Windows\\System32",
                "del", DUMP_DESTINATION + backupFileName + ".sql");
    }


}

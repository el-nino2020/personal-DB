package service;

import com.google.common.base.Preconditions;
import common.Param;
import dao.DirectoryInfoDAO;
import domain.DirectoryInfo;
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
    private DirectoryInfoDAO directoryInfoDAO = new DirectoryInfoDAO();

    public DBService(AccountService accountService) {
        this.accountService = accountService;
    }


    /**
     * 从directories中获取所有表的信息
     *
     * @return 包含所有表的信息的List，按照表名升序排列
     */
    public List<DirectoryInfo> getAllDirectoryInfo() {
        Preconditions.checkState(accountService.getLoginStatus(), "数据库账户未登录");

        List<DirectoryInfo> ans = directoryInfoDAO.queryMultiRow(accountService.getConnection(),
                "SELECT * FROM directories ORDER BY dirname;",
                DirectoryInfo.class);
        return ans;
    }


    public boolean directoryExists(String dirName) {
        Preconditions.checkState(accountService.getLoginStatus(), "数据库账户未登录");
        Preconditions.checkNotNull(dirName);

        List<String> dirNames = getAllDirectoryNames();
        return dirNames.contains(dirName);
    }

    public List<String> getAllDirectoryNames() {
        Preconditions.checkState(accountService.getLoginStatus(), "数据库账户未登录");
//TODO 这个方法需要优化
        List<DirectoryInfo> list = getAllDirectoryInfo();
        ArrayList<String> ans = new ArrayList<>(list.size());
        for (DirectoryInfo directoryInfo : list) {
            ans.add(directoryInfo.getDirname());
        }
        return ans;

    }


    /**
     * 找到files表的AUTO_INCREMENT的下一个值。参考：
     * https://stackoverflow.com/questions/15821532/get-current-auto-increment-value-for-any-table
     *
     * @return value(AUTO_INCREMENT) + ""，把这个值以字符串形式返回
     */
    public String getFilesAutoIncrementValue() {
        Preconditions.checkState(accountService.getLoginStatus(), "数据库账户未登录");

        Connection connection = accountService.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String ans = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SHOW TABLE STATUS FROM cloud_backup WHERE `name` LIKE ? ");
            preparedStatement.setString(1, "files");

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

        String backupFileName = AccountService.DATABASE + "_" +
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
     * 在directories中插入一条新记录
     */
    public void createNewDirectory(DirectoryInfo directoryInfo) {
        Preconditions.checkState(accountService.getLoginStatus(), "数据库账户未登录");
        Preconditions.checkNotNull(directoryInfo);
        Preconditions.checkArgument(!directoryExists(directoryInfo.getDirname()),
                String.format("%s表已存在，无法再次创建", directoryInfo.getDirname()));

        Connection connection = accountService.getConnection();

        try {

            directoryInfoDAO.update(connection,
                    "INSERT INTO directories(dirname, note) VALUES (?, ?)",
                    directoryInfo.getDirname(), directoryInfo.getNote());

            Utility.assertion(directoryExists(directoryInfo.getDirname()),
                    String.format("%s表创建失败", directoryInfo.getDirname()));

            System.out.format("%s表创建成功\n", directoryInfo.getDirname());

            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            e.printStackTrace();
        }


        databaseDump();
    }

    public void commit() {
        try {
            accountService.getConnection().commit();
        } catch (SQLException e) {
            throw new RuntimeException("数据库提交失败");
        }
    }


    public void rollback() {
        try {
            accountService.getConnection().rollback();
        } catch (SQLException e) {
            throw new RuntimeException("数据库回滚失败");
        }
    }

}

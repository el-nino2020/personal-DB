package service;

import com.google.common.base.Preconditions;
import dao.TableInfoDAO;
import domain.TableInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 负责数据库的备份和已有表的查询，也许还要负责新建表？
 */
public class DBService {
    AccountService accountService;
    TableInfoDAO tableInfoDAO = new TableInfoDAO();

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
}

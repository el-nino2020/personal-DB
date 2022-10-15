package service;

import com.google.common.base.Preconditions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 负责数据库的备份和已有表的查询，也许还要负责新建表？
 */
public class DBService {
    AccountService accountService;

    public DBService(AccountService accountService) {
        this.accountService = accountService;
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
}

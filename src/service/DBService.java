package service;

import com.google.common.base.Preconditions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

        Connection connection = accountService.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean ans = false;
        try {
            preparedStatement = connection.prepareStatement("SHOW TABLES;");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next() && !ans) {
                if (tableName.equals(resultSet.getString(1)))
                    ans = true;
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

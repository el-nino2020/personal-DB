package service;

import common.Param;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 负责登录DBMS，并提供Connection对象
 */
public class AccountService {
    private static final String DBMS_URL =
            String.format("jdbc:mysql://%s:%s/%s",
                    Param.MYSQL_IP, Param.MYSQL_PORT, Param.DATABASE_NAME);
    public static final String USER = Param.DBMS_USER;
    public static final String DATABASE = Param.DATABASE_NAME;

    private Connection connection;
    private boolean loginStatus = false;
    private transient String DBMSPassword = null;//登录成功后，该字段保存USER的密码，主要用于数据库备份


    public AccountService() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");//加载类,自动注册
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean loginDBMS(String password) {
        if (loginStatus) return true;

        try {
            connection = DriverManager.getConnection(DBMS_URL, USER, password);
            DBMSPassword = password;
        } catch (SQLException e) {
            System.out.println(e);
        }
        if (connection != null) {
            loginStatus = true;
        }
        return connection != null;
    }

    public boolean getLoginStatus() {
        return loginStatus;
    }

    /**
     * 只能被其他Service调用
     */
    String getDBMSPassword() {
        return DBMSPassword;
    }

    public void quitAccount() {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 只能给同一个package中的其他service类用
     *
     * @return
     */
    Connection getConnection() {
        return connection;
    }
}

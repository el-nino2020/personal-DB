package dao;


import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @param <T> 某个Domain类
 */
public class BasicDAO<T> {
    private QueryRunner qr = new QueryRunner();
    private Connection connection;


    public BasicDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * @param sql        DML语句
     * @param parameters 替换sql中的 ? 占位符
     * @return 受影响的行数
     */
    public int update(String sql, Object... parameters) {
        Connection connection = null;

        try {
            return qr.update(connection, sql, parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param sql        查询语句
     * @param clazz      Domain类.class
     * @param parameters 替换sql中的占位符 ?
     * @return 多行记录
     */
    public List<T> queryMultiRow(String sql, Class<T> clazz, Object... parameters) {
        Connection connection = null;

        try {
            return qr.query(connection, sql,
                    new BeanListHandler<T>(clazz), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param sql        查询语句
     * @param clazz      Domain类.class
     * @param parameters 替换sql中的占位符 ?
     * @return 单行记录
     */
    public T querySingleRow(String sql, Class<T> clazz, Object... parameters) {
        Connection connection = null;

        try {
            return qr.query(connection, sql,
                    new BeanHandler<T>(clazz), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @param sql        查询语句
     * @param parameters 替换sql中的占位符 ?
     * @return 单行单列的数据
     */
    public Object queryScalar(String sql, Object... parameters) {
        Connection connection = null;

        try {
            return qr.query(connection, sql, new ScalarHandler(), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

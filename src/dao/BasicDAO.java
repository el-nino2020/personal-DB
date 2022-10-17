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

    /**
     * DML语句，使用事务保证数据一致与安全
     * @param sql        DML语句
     * @param parameters 替换sql中的 ? 占位符
     * @return 受影响的行数
     */
    public int update(Connection connection, String sql, Object... parameters) {
        //https://commons.apache.org/proper/commons-dbutils/apidocs
        //This Connection must be in auto-commit mode or the update will not be saved.
        int ans;
        try {
            connection.setAutoCommit(false);
            ans = qr.update(connection, sql, parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ans;
    }

    /**
     * @param sql        查询语句
     * @param clazz      Domain类.class
     * @param parameters 替换sql中的占位符 ?
     * @return 多行记录
     */
    public List<T> queryMultiRow(Connection connection, String sql, Class<T> clazz, Object... parameters) {

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
    public T querySingleRow(Connection connection, String sql, Class<T> clazz, Object... parameters) {

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
    @SuppressWarnings({"unchecked"})
    public Object queryScalar(Connection connection, String sql, Object... parameters) {
        try {
            return qr.query(connection, sql, new ScalarHandler(), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

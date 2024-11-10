package coffee.util;

import java.sql.*;

/**
 * @author yu
 * @Description: JDBC工具栏，数据库连接与数据库操作相关方法
 * @date 2021/6/20 10:27
 */
public class BaseDAO {
    private String url = "jdbc:mysql://localhost:3306/graduation_288_coffee?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    private String username = "root";
    private String password = "123456";
    private Connection conn = null;
    private ResultSet rs = null;
    private Statement stat = null;
    private PreparedStatement prep = null;


    public BaseDAO() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = this.getConn();
            stat = this.createstat();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Connection getConn() {
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public Statement createstat() {
        try {
            stat = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stat;
    }

    public PreparedStatement createPrep(String sql) {
        try {
            prep = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prep;
    }

    public ResultSet executeQuery() {
        try {
            rs = prep.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet executeQuery(String sql) {
        try {
            rs = stat.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public int executeUpdate(String sql) {
        int iMark = 0;
        try {
            iMark = stat.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return iMark;
    }

    public int executeUpdate() {
        int iMark = 0;
        try {
            iMark = prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return iMark;
    }

    public void close() {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stat != null) {
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


}

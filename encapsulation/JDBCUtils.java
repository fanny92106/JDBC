package com.atguigu.connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Utils to manipulate database
 */
public class JDBCUtils {
    /**
     * @encapsulation: get DB connection
     */
    public static Connection getConnection() throws IOException, ClassNotFoundException, SQLException {
        InputStream is = PreparedStatementUpdateTest.class.getClassLoader().getResourceAsStream("jdbc.properties");

        Properties pros = new Properties();
        pros.load(is);

        String user = pros.getProperty("user");
        String url = pros.getProperty("url");
        String password = pros.getProperty("password");
        String driver = pros.getProperty("driver");

        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    /**
     * @encapsulation: close resource
     */
    public static void closeResource(Connection conn, Statement ps) {
        try {
            if(ps != null) {
                ps.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            if(conn !=null) {
                conn.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * @encapsulation: close resource
     * @overload
     */
    public static void closeResource(Connection conn, Statement ps, ResultSet rs) {
        try {
            if(ps != null) {
                ps.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            if(conn !=null) {
                conn.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

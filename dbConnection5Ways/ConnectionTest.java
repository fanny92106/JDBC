package com.atguigu.connection;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionTest {

    // solution 1: mysql driver.connect (use third party api)
    @Test
    public void testConnection1() throws SQLException {
        // get driver instance provided by third party api
        Driver driver = new com.mysql.cj.jdbc.Driver();

        String url = "jdbc:mysql://localhost:3306/sita";
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "BJjjl2015!");
        Connection conn = driver.connect(url, info);
        System.out.println(conn);
    }

    // solution 2: not use third party api in order to have better portability
    @Test
    public void testConnection2() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        // get driver instance by using reflection
        Class clazz = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver = (Driver)clazz.newInstance();

        String url = "jdbc:mysql://localhost:3306/sita";
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "BJjjl2015!");
        Connection conn = driver.connect(url, info);
        System.out.println(conn);
    }

    // solution 3: use DriverManager (class)
    @Test
    public void testConnection3() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        // get driver instance by using reflection
        Class clazz = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver = (Driver)clazz.newInstance();

        String url = "jdbc:mysql://localhost:3306/sita";
        String user = "root";
        String password = "BJjjl2015!";

        // load & register driver & get connection by using DriverManager class
        DriverManager.registerDriver(driver);
        Connection conn = DriverManager.getConnection(url, user, password);

        System.out.println(conn);
    }

    // solution 4: 只加载驱动，无需显示地注册驱动，因为driver里面的静态代码块已经帮做了
    @Test
    public void testConnection4() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        String url = "jdbc:mysql://localhost:3306/sita";
        String user = "root";
        String password = "BJjjl2015!";

        // get driver instance by using reflection
        Class.forName("com.mysql.cj.jdbc.Driver");
//        Driver driver = (Driver)clazz.newInstance();
//        DriverManager.registerDriver(driver);

        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }

    // solution 5 (final): put info in resource file
    /**
     * Advantages:
     *  1. 实现了数据和代码的分离，实现了解耦
     *  2. 如果需要修改配置文件信息，可以避免程序重新打包
     */
    @Test
    public void testConnection5() throws ClassNotFoundException, SQLException, IOException {

        // read config info from resource file
        InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");

        Properties pros = new Properties();
        pros.load(is);

        String user = pros.getProperty("user");
        String url = pros.getProperty("url");
        String password = pros.getProperty("password");
        String driver = pros.getProperty("driver");

        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }
}

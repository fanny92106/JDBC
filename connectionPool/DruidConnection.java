package com.atguigu.connection;


import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class DruidConnection {

    private static DataSource ds;

    // create data source once at class loading time
    static {
        try {
            Properties pros = new Properties();
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druidJDBC.properties");
            pros.load(is);
            ds = DruidDataSourceFactory.createDataSource(pros);
            System.out.println(ds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // get db connection
    public static Connection getConnection() throws Exception {
        Connection conn = ds.getConnection();
        System.out.println(conn);
        return conn;
    }
}

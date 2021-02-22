package com.atguigu.connection;

import com.atguigu.bean.Employee;
import com.atguigu.bean.Order;
import com.sun.tools.corba.se.idl.constExpr.Or;
import org.junit.Test;

import javax.xml.transform.Result;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EncapsulationForSelectStatement {

    @Test
    public void testForOne() {
        String sql = "select id, name, gender from `employee` where id = ?";
        Employee employee = getForOne(Employee.class, sql, 5);

        String sql2 = "select order_id orderId, order_name orderName, order_date orderDate from `order` where order_id = ?";
        Order order = getForOne(Order.class, sql2, 4);
    }

    @Test
    public void testForAll() {
        String sql = "select * from `employee` where id > ?";
        List<Employee> list = getForAll(Employee.class, sql, 2);

        String sql1 = "select order_id orderId, order_name orderName, order_date orderDate from `order` where order_id <= ?";
        List<Order> list1 = getForAll(Order.class, sql1, 4);
    }

    /**
     * @encapsulation: select multiple records
     */
    public <T> List<T> getForAll(Class<T> clazz, String sql, Object...args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            List<T> list = new ArrayList<>();
            while(rs.next()) {
                // reflection
                T t = clazz.newInstance();
                // get column name and column value
                for(int i = 0; i < columnCount; i++) {
                    // get colValue from rs
                    Object colValue = rs.getObject(i + 1);
                    // get colLabel
                    String colName = rsmd.getColumnLabel(i + 1);
                    // reflection
                    Field field = clazz.getDeclaredField(colName);
                    field.setAccessible(true);
                    field.set(t, colValue);
                }
                list.add(t);
            }
            System.out.println(list);
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps, rs);
        }
        return null;
    }

    /**
     *
     * @encapsulation: select one record
     */
    public <T> T getForOne(Class<T> clazz, String sql, Object...args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            if(rs.next()) {
                // reflection
                T t = clazz.newInstance();

                // get column name and column value
                for(int i = 0; i < columnCount; i++) {
                    // get colValue from rs
                    Object colValue = rs.getObject(i + 1);
                    // get colLabel
                    String colName = rsmd.getColumnLabel(i + 1);
                    // reflection
                    Field field = clazz.getDeclaredField(colName);
                    field.setAccessible(true);
                    field.set(t, colValue);
                }
                System.out.println(t);
                return t;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps, rs);
        }
        return null;
    }
}


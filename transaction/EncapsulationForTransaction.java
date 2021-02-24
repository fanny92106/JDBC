package com.atguigu.connection;

import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class EncapsulationForTransaction {
    /**
     * not involve Transaction
     */
    @Test
    public void testWithoutTransaction() throws SQLException, IOException, ClassNotFoundException {
        // will succeed
        String sql1 = "update account set balance = balance - 100 where id = ?";
        PreparedStatementTest.updateWithoutTransaction(sql1, 1);

        // mimic network issue
        System.out.println(10 / 0);

        // will fail
        String sql2 = "update account set balance = balance - 100 where id = ?";
        PreparedStatementTest.updateWithoutTransaction(sql2, 2);
    }

    /**
     * encapsulation: involve Transaction
     */
    @Test
    public void testTransaction() {
        // keep connection as shared resource between two operation in order to not
        // conduct auto-commit after the first operation closes resource
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();

            // set DML auto-commit as false
            conn.setAutoCommit(false);

            String sql1 = "update account set balance = balance + 100 where id = ?";
            PreparedStatementTest.updateWithTransaction(conn, sql1, 1);

            // mimic network issue
            System.out.println(10 / 0);

            String sql2 = "update account set balance = balance - 100 where id = ?";
            PreparedStatementTest.updateWithTransaction(conn, sql2, 2);

            // if success will commit both
            conn.commit();

        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                // if not success will rollback both
                conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } finally {
            try {
                // set autoCommit back to true
                // just in case other operation use the same connection
                conn.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            // close resource
            JDBCUtils.closeResource(conn, null);
        }
    }
}


package com.df4j.xcoria.test.jdbc;

import com.df4j.xcoria.jdbc.ConnectionUtils;
import com.df4j.xcoria.test.utils.CloseUtilsTest;
import com.df4j.xcoria.utils.CloseUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import static org.testng.Assert.*;

public class ConnectionUtilsTest {
    private String driverClassName = "org.h2.Driver";
    private String jdbcUrl = "jdbc:h2:mem:test";
    private String username = "sa";

    @Test
    public void testConnect() {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        Throwable t = null;
        try {
            con = ConnectionUtils.connect(driverClassName, jdbcUrl, username, null);
            stmt = con.createStatement();
            rs = stmt.executeQuery("select 2 from dual");
            rs.next();
            String v = rs.getString(1);
            Assert.assertEquals(v, "2");
        } catch (Exception e) {
            t = e;
        } finally {
            CloseUtils.closeAll(rs, stmt, con);
        }
        Assert.assertNull(t);
    }

    @Test
    public void testTestConnect() {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        Throwable t = null;
        Properties props = new Properties();
        try {
            con = ConnectionUtils.connect(driverClassName, jdbcUrl, username, null, props);
            stmt = con.createStatement();
            rs = stmt.executeQuery("select 2 from dual");
            rs.next();
            String v = rs.getString(1);
            Assert.assertEquals(v, "2");
        } catch (Exception e) {
            t = e;
        } finally {
            CloseUtils.closeAll(rs, stmt, con);
        }
        Assert.assertNull(t);
    }

    @Test
    public void testGetDriver() throws ClassNotFoundException {
        String driverClassName2 = "com.df4j.xcoria.test.jdbc.MyH2Driver";
        Driver driver = ConnectionUtils.getDriver(driverClassName2);
        Assert.assertNotNull(driver);
        Assert.assertEquals(driver.getClass().getName(), driverClassName2);
        Class.forName(driverClassName2);
        Driver driver2 = ConnectionUtils.getDriver(driverClassName2);
        Assert.assertNotNull(driver2);
        Assert.assertEquals(driver2.getClass().getName(), driverClassName2);
        Assert.assertNotEquals(driver, driver2);
    }

    @Test
    public void testGetAndSetAutoCommit() {
        Connection con = ConnectionUtils.connect(driverClassName, jdbcUrl, username, null);
        ConnectionUtils.setAutoCommit(con, true);
        Assert.assertTrue(ConnectionUtils.getAutoCommit(con));
        ConnectionUtils.setAutoCommit(con, false);
        Assert.assertFalse(ConnectionUtils.getAutoCommit(con));
    }

    @Test
    public void testCommit() {
        Connection con = ConnectionUtils.connect(driverClassName, jdbcUrl, username, null);
        ConnectionUtils.commit(con);
    }

    @Test
    public void testRollback() {
        Connection con = ConnectionUtils.connect(driverClassName, jdbcUrl, username, null);
        ConnectionUtils.rollback(con);
    }

    @Test
    public void testSetAndGetTransactionIsolation() {
        Connection con = ConnectionUtils.connect(driverClassName, jdbcUrl, username, null);
        ConnectionUtils.setTransactionIsolation(con, 1);
        Assert.assertEquals(ConnectionUtils.getTransactionIsolation(con), 1);
        ConnectionUtils.setTransactionIsolation(con, 2);
        Assert.assertEquals(ConnectionUtils.getTransactionIsolation(con), 2);
    }

    @Test
    public void testExecuteTransaction() {
        Connection con = ConnectionUtils.connect(driverClassName, jdbcUrl, username, null);
        String res = ConnectionUtils.executeTransaction(con, x -> {
            String v = null;
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select 1 from dual");
                rs.next();
                v = rs.getString(1);
            } catch (Exception e) {

            }
            return v;
        });
        Assert.assertEquals(res, "1");
    }
}
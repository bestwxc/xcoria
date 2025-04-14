package com.df4j.xcoria.jdbc;

import com.df4j.xcoria.exception.XcoriaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.function.Function;

public class ConnectionUtils {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionUtils.class);

    // 连接jdbc时，用户名的属性名
    private static final String JDBC_PROP_USER = "user";
    // 连接jdbc时，密码的属性名
    private static final String JDBC_PROP_PASSWORD = "password";


    /**
     * 建立jdbc连接
     *
     * @param driverClassName 驱动类
     * @param url             地址
     * @param user            用户名
     * @param pwd             密码
     * @return jdbc连接
     */
    public static Connection connect(String driverClassName, String url, String user, String pwd) {
        return connect(driverClassName, url, user, pwd, null);
    }

    /**
     * 建立jdbc连接
     *
     * @param driverClassName 驱动类
     * @param url             地址
     * @param user            用户名
     * @param pwd             密码
     * @param props           属性
     * @return jdbc连接
     */
    public static Connection connect(String driverClassName, String url, String user, String pwd, Properties props) {
        Driver driver = getDriver(driverClassName);
        if (driver == null) {
            try {
                driver = DriverManager.getDriver(url);
            } catch (SQLException e) {
                throw new XcoriaException("根据url获取驱动类异常", e);
            }
        }
        Properties tmp = props == null ? new Properties() : (Properties) props.clone();
        if (user != null) {
            tmp.setProperty(JDBC_PROP_USER, user);
        }
        if (pwd != null) {
            tmp.setProperty(JDBC_PROP_PASSWORD, pwd);
        }
        try {
            return driver.connect(url, tmp);
        } catch (SQLException e) {
            throw new XcoriaException("调用驱动类创建jdbc连接失败", e);
        }
    }


    /**
     * 获取driverClassName对应的驱动类
     *
     * @param driverClassName 驱动类全类名
     * @return jdbc驱动类
     */
    public static Driver getDriver(String driverClassName) {
        Driver driver = null;
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver d = drivers.nextElement();
            if (d.getClass().getName().equals(driverClassName)) {
                driver = d;
                break;
            }
        }
        if (driver == null) {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Class<Driver> driverClass = null;
            if (classLoader != null) {
                try {
                    driverClass = (Class<Driver>) classLoader.loadClass(driverClassName);
                } catch (ClassNotFoundException e) {
                    logger.info("从ContextClassLoader加载驱动类失败,driverClassName: {}", driverClassName);
                }
            }
            if (driverClass == null) {
                classLoader = ConnectionUtils.class.getClassLoader();
                try {
                    driverClass = (Class<Driver>) classLoader.loadClass(driverClassName);
                } catch (ClassNotFoundException e) {
                    logger.info("classLoader,driverClassName: {}", driverClassName);
                }
            }
            if (driverClass != null) {
                try {
                    driver = driverClass.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    logger.info("实例化驱动失败", e);
                }
            }
        }
        return driver;
    }

    /**
     * 获取当前是否开启AutoCommit
     *
     * @param con 当前连接
     * @return 是否开启AutoCommit
     */
    public static boolean getAutoCommit(Connection con) {
        try {
            return con.getAutoCommit();
        } catch (SQLException e) {
            throw new XcoriaException("获取autoCommit失败", e);
        }
    }

    /**
     * 设置AutoCommit
     *
     * @param conn       连接
     * @param autoCommit 是否AutoCommit
     */
    public static void setAutoCommit(Connection conn, boolean autoCommit) {
        try {
            conn.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            throw new XcoriaException("设置autoCommit失败", e);
        }
    }

    /**
     * 提交事务
     *
     * @param con 连接
     */
    public static void commit(Connection con) {
        try {
            con.commit();
        } catch (SQLException e) {
            throw new XcoriaException("提交事务失败", e);
        }
    }

    /**
     * 回滚
     *
     * @param con 连接
     */
    public static void rollback(Connection con) {
        try {
            con.rollback();
        } catch (SQLException e) {
            throw new XcoriaException("回滚事务失败", e);
        }
    }

    /**
     * 开启连接事务，并执行逻辑
     *
     * @param con    数据库连接
     * @param action 执行逻辑
     * @param <T>    返回类型
     * @return 执行结果
     */
    public static <T> T executeTransaction(Connection con, Function<Connection, T> action) {
        boolean autoCommit = getAutoCommit(con);
        try {
            if (!autoCommit) {
                setAutoCommit(con, true);
            }
            T t = action.apply(con);
            commit(con);
            return t;
        } catch (Exception e) {
            rollback(con);
            throw new XcoriaException("执行", e);
        } finally {
            if (!autoCommit) {
                setAutoCommit(con, false);
            }
        }
    }
}

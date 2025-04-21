package com.df4j.xcoria.test.jdbc;

import org.h2.Driver;
import org.h2.message.DbException;

import java.sql.DriverManager;
import java.sql.SQLException;

public class MyH2Driver extends Driver {
    private static final Driver INSTANCE = new MyH2Driver();
    private static boolean registered;
    static {
        load();
    }
    public static synchronized Driver load() {
        try {
            if (!registered) {
                registered = true;
                DriverManager.registerDriver(INSTANCE);
            }
        } catch (SQLException e) {
            DbException.traceThrowable(e);
        }
        return INSTANCE;
    }
}

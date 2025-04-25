package com.df4j.xcoria.test.utils;

import com.df4j.xcoria.utils.DependencyCheckUtils;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class DependencyCheckUtilsTest {

    @Test
    public void testCheck() {
        boolean flag = DependencyCheckUtils.check("com.mysql", "mysql-connector-java", "com.mysql.cj.jdbc.Driver");
        assertFalse(flag);
        flag = DependencyCheckUtils.check("com.h2database", "h2", "org.h2.Driver");
        assertTrue(flag);
    }
}
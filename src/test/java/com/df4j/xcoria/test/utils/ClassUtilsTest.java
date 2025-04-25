package com.df4j.xcoria.test.utils;

import org.h2.Driver;
import org.testng.annotations.Test;

import static com.df4j.xcoria.utils.ClassUtils.*;
import static org.testng.Assert.*;

public class ClassUtilsTest {

    @Test
    public void testCheckClassExists() {
        boolean flag = checkClassExists(getClass().getClassLoader(), "com.oracle.jdbc.Driver");
        assertFalse(flag);
        flag = checkClassExists(getClass().getClassLoader(), "org.h2.Driver");
        assertTrue(flag);
    }

    @Test
    public void testCheckAllClassExists() {
        boolean flag = checkAllClassExists(getClass().getClassLoader(), "com.oracle.jdbc.Driver", "org.h2.Driver");
        assertFalse(flag);
        flag = checkAllClassExists(getClass().getClassLoader(), "com.oracle.jdbc.Driver", "org.h2.Driver");
        assertFalse(flag);
        flag = checkAllClassExists(getClass().getClassLoader(), "org.h2.Driver", "org.slf4j.Logger");
        assertTrue(flag);
    }

    @Test
    public void testLoadClass() {
        Class<?> clazz = loadClass("org.h2.Driver");
        assertEquals(Driver.class, clazz);
    }

    @Test
    public void testTestLoadClass() {
        Class<?> clazz = loadClass("org.h2.Driver", getClass().getClassLoader());
        assertEquals(Driver.class, clazz);
    }

    @Test
    public void testGetDefaultClassLoader() {
        ClassLoader classLoader = getDefaultClassLoader();
        assertNotNull(classLoader);
        assertTrue(classLoader instanceof ClassLoader);
    }
}
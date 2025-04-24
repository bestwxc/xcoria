package com.df4j.xcoria.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassUtils {

    private static final Logger logger = LoggerFactory.getLogger(ClassUtils.class);

    /**
     * 判断指定的类是否存在
     *
     * @param className   全类名
     * @param classLoader 类加载器
     * @return 是否存在
     */
    public static boolean checkClassExists(ClassLoader classLoader, String className) {
        try {
            Class.forName(className, true, classLoader);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * 判断指定的所有类是否存在
     *
     * @param classLoader 类加载器
     * @param classNames  全类名列表
     * @return 是否存在
     */
    public static boolean checkAllClassExists(ClassLoader classLoader, String... classNames) {
        boolean flag = true;
        for (String className : classNames) {
            if (!checkClassExists(classLoader, className)) {
                logger.info("指定类不存在, className: {}", className);
                flag = false;
                break;
            }
        }
        return flag;
    }

    /**
     * 加载指定类
     *
     * @param className 全类名
     * @return 指定类
     */
    public static Class<?> loadClass(String className) {
        return loadClass(className, getDefaultClassLoader());
    }

    /**
     * 加载指定类
     *
     * @param className 全类名
     * @param loader    类加载器
     * @return 指定类
     */
    public static Class<?> loadClass(String className, ClassLoader loader) {
        try {
            return Class.forName(className, true, loader);
        } catch (ClassNotFoundException e) {
            logger.warn("指定类不存在, className: " + className, e);
        }
        return null;
    }

    /**
     * 获取默认的ClassLoader
     *
     * @return ClassLoader
     */
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = ClassLoader.getSystemClassLoader();
        }
        return classLoader;
    }
}

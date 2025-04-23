package com.df4j.xcoria.utils;

import com.df4j.xcoria.exception.XcoriaException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ReflectUtils {

    /**
     * 遍历目标类定义的字段，并执行action
     *
     * @param clazz  目标类
     * @param action action
     */
    public static void doWithLocalFields(Class<?> clazz, Consumer<Field> action) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                action.accept(field);
            } catch (Exception e) {
                throw new XcoriaException("action on filed " + field.getName() + " error", e);
            }
        }
    }

    /**
     * 遍历目标类所有的字段（包括父类），并执行action
     *
     * @param clazz  目标类
     * @param action action
     */
    public static void doWithFields(Class<?> clazz, Consumer<Field> action) {
        doWithFields(clazz, action, null);
    }

    /**
     * 遍历目标类所有的字段（包括父类），并执行action
     *
     * @param clazz  目标类
     * @param action action
     * @param filter filter
     */
    public static void doWithFields(Class<?> clazz, Consumer<Field> action, Predicate<Field> filter) {
        Class<?> targetClass = clazz;
        do {
            Field[] fields = targetClass.getDeclaredFields();
            for (Field field : fields) {
                if (filter != null && !filter.test(field)) {
                    continue;
                }
                try {
                    action.accept(field);
                } catch (Exception e) {
                    throw new XcoriaException("action on filed " + field.getName() + " error", e);
                }
            }
            targetClass = targetClass.getSuperclass();
        } while (targetClass != null && targetClass != Object.class);
    }

    /**
     * 遍历目标类定义的方法，并执行action
     *
     * @param clazz  目标类
     * @param action action
     */
    public static void doWithLocalMethods(Class<?> clazz, Consumer<Method> action) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            try {
                action.accept(method);
            } catch (Exception e) {
                throw new XcoriaException("action on method " + method.getName() + " error", e);
            }
        }
    }

    /**
     * 遍历目标类所有的方法（包括父类），并执行action
     *
     * @param clazz  目标类
     * @param action action
     */
    public static void doWithMethods(Class<?> clazz, Consumer<Method> action) {
        doWithMethods(clazz, action, null);
    }

    /**
     * 遍历目标类所有的方法（包括父类），并执行action
     *
     * @param clazz  目标类
     * @param action action
     * @param filter filter
     */
    public static void doWithMethods(Class<?> clazz, Consumer<Method> action, Predicate<Method> filter) {
        Class<?> targetClass = clazz;
        do {
            Method[] methods = targetClass.getDeclaredMethods();
            for (Method method : methods) {
                if (filter != null && !filter.test(method)) {
                    continue;
                }
                try {
                    action.accept(method);
                } catch (Exception e) {
                    throw new XcoriaException("action on method " + method.getName() + " error", e);
                }
            }
            targetClass = targetClass.getSuperclass();
        } while (targetClass != null && targetClass != Object.class);
    }

    /**
     * 查找指定名称的field
     *
     * @param clazz 目标类
     * @param name  指定名字
     * @return 字段
     */
    public static Field findField(Class<?> clazz, String name) {
        Field returnField = null;
        Class<?> targetClass = clazz;
        do {
            Field[] fields = targetClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals(name)) {
                    returnField = field;
                    break;
                }
            }
            targetClass = targetClass.getSuperclass();
        } while (targetClass != null && targetClass != Object.class);
        return returnField;
    }

    /**
     * 查找指定名称的方法
     *
     * @param clazz 目标类
     * @param name  方法名
     * @return 方法
     */
    public static Method findMethod(Class<?> clazz, String name) {
        return findMethod(clazz, name, new Class<?>[0]);
    }

    /**
     * 查找指定方法
     *
     * @param clazz          目标类
     * @param name           方法名
     * @param parameterTypes 方法参数
     * @return 方法
     */
    public static Method findMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
        Method returnMethod = null;
        Class<?> targetClass = clazz;
        do {
            Method[] methods = targetClass.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(name) && Arrays.equals(method.getParameterTypes(), parameterTypes)) {
                    returnMethod = method;
                    break;
                }
            }
            targetClass = targetClass.getSuperclass();
        } while (targetClass != null && targetClass != Object.class);
        return returnMethod;
    }
}

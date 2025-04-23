package com.df4j.xcoria.test.utils;

import com.df4j.xcoria.test.bean.Student;
import com.df4j.xcoria.utils.ReflectUtils;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class ReflectUtilsTest {

    @Test
    public void testDoWithLocalFields() {
        List<Field> fields = new ArrayList<>();
        ReflectUtils.doWithLocalFields(Student.class, field -> {
            fields.add(field);
        });
        assertEquals(fields.size(), 1);
    }

    @Test
    public void testDoWithFields() {
        List<Field> fields = new ArrayList<>();
        ReflectUtils.doWithFields(Student.class, field -> {
            fields.add(field);
        });
        assertEquals(fields.size(), 3);
    }

    @Test
    public void testTestDoWithFields() {
        List<Field> fields = new ArrayList<>();
        ReflectUtils.doWithFields(Student.class, field -> {
            fields.add(field);
        }, field -> field.getName().equals("name"));
        assertEquals(fields.size(), 1);
    }

    @Test
    public void testDoWithLocalMethods() {
        List<Method> methods = new ArrayList<>();
        ReflectUtils.doWithLocalMethods(Student.class, method -> {
            methods.add(method);
        });
        assertEquals(methods.size(), 2);
    }

    @Test
    public void testDoWithMethods() {
        List<Method> methods = new ArrayList<>();
        ReflectUtils.doWithMethods(Student.class, method -> {
            methods.add(method);
        });
        assertEquals(methods.size(), 6);
    }

    @Test
    public void testTestDoWithMethods() {
        List<Method> methods = new ArrayList<>();
        ReflectUtils.doWithMethods(Student.class, method -> {
            methods.add(method);
        }, method -> method.getName().equals("getName"));
        assertEquals(methods.size(), 1);
    }

    @Test
    public void testFindField() {
        Field field = ReflectUtils.findField(Student.class, "name");
        assertEquals(field.getName(), "name");
    }

    @Test
    public void testFindMethod() {
        Method method = ReflectUtils.findMethod(Student.class, "getName");
        assertEquals(method.getName(), "getName");
    }

    @Test
    public void testTestFindMethod() {
        Method method = ReflectUtils.findMethod(Student.class, "setName", new Class[]{String.class});
        assertEquals(method.getName(), "setName");
    }

    @Test
    public void testSetField() {
        Student student = new Student();
        Field field = ReflectUtils.findField(Student.class, "name");
        field.setAccessible(true);
        ReflectUtils.setField(field, student, "name1");
        assertEquals(student.getName(), "name1");
    }

    @Test
    public void testGetField() {
        Student student = new Student();
        student.setName("name1");
        Field field = ReflectUtils.findField(Student.class, "name");
        field.setAccessible(true);
        String name = (String) ReflectUtils.getField(field, student);
        assertEquals("name1", name);
    }

    @Test
    public void testInvokeMethod() {
        Student student = new Student();
        student.setName("name1");
        Method method = ReflectUtils.findMethod(Student.class, "getName");
        String name = (String) ReflectUtils.invokeMethod(method, student);
        assertEquals("name1", name);
    }

    @Test
    public void testTestInvokeMethod() {
        Student student = new Student();
        Method method = ReflectUtils.findMethod(Student.class, "setName", new Class[]{String.class});
        ReflectUtils.invokeMethod(method, student, "name1");
        assertEquals("name1", student.getName());
    }
}
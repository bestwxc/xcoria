package com.df4j.xcoria.test.utils;

import com.df4j.xcoria.test.bean.Student;
import com.df4j.xcoria.utils.PojoUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class PojoUtilsTest {

    @Test
    public void testTestSafeEquals() {
        Student student1 = new Student("zhangsan", 1, "math");
        Assert.assertTrue(PojoUtils.safeEquals(student1, student1));
        Assert.assertTrue(PojoUtils.safeEquals(null, null));
        Assert.assertFalse(PojoUtils.safeEquals(student1, null));
        Assert.assertFalse(PojoUtils.safeEquals(null, student1));
    }

    @Test
    public void testToMap() {
        Student student = new Student("zhangsan", 1, "math");
        Map<String, Object> studentMap = new HashMap<>();
        studentMap.put("name", "zhangsan");
        studentMap.put("age", 1);
        studentMap.put("course", "math");
        Map<String, Object> parsedMap = PojoUtils.toMap(student);
        Assert.assertEquals(parsedMap, studentMap);
    }
}
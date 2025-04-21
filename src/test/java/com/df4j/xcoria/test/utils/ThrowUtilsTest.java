package com.df4j.xcoria.test.utils;

import com.df4j.xcoria.exception.XcoriaException;
import com.df4j.xcoria.utils.ThrowUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ThrowUtilsTest {

    @Test
    public void testRethrow() {
        RuntimeException re = new RuntimeException();
        XcoriaException xe = new XcoriaException(re);
        Throwable t = null;
        try{
            ThrowUtils.rethrow("", xe);
        }catch (Throwable t2){
            t = t2;
        }
        Assert.assertEquals(t, xe);

        t = null;
        try{
            ThrowUtils.rethrow("", re);
        }catch (Throwable t2){
            t = t2;
        }
        Assert.assertTrue(t instanceof XcoriaException);
        Assert.assertEquals(t.getCause(), re);
    }

    @Test
    public void testError() {
        Throwable t = null;
        try {
            ThrowUtils.error("测试错误,%s, %s, %d", "mark1", "mark2", 2);
        }catch (Exception e) {
            t = e;
        }
        Assert.assertNotNull(t);
        Assert.assertTrue(t instanceof XcoriaException);
        Assert.assertEquals(String.format("测试错误,%s, %s, %d", "mark1", "mark2", 2), t.getMessage());
    }
}
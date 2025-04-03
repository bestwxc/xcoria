package com.df4j.xcoria.utils;

import com.df4j.xcoria.exception.XcoriaException;

public class ThrowUtils {


    /**
     * 重新抛出异常，如果已经是XcoriaException，则直接抛出
     *
     * @param msg 异常信息
     * @param e   原异常
     * @throws Throwable 异常，供外层捕获
     */
    public static void rethrow(String msg, Throwable e) throws Throwable {
        XcoriaException xc = null;
        if (e instanceof XcoriaException) {
            xc = (XcoriaException) e;
        } else {
            xc = new XcoriaException(msg, e);
        }
        throw xc;
    }


    /**
     * 抛出异常
     *
     * @param tpl  异常信息模板，使用String.format处理
     * @param args 模板处理变量
     */
    public static void error(String tpl, Object... args) {
        String msg = String.format(tpl, args);
        throw new XcoriaException(msg);
    }
}

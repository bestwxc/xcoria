package com.df4j.xcoria.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CloseUtils {

    private static Logger logger = LoggerFactory.getLogger(CloseUtils.class);

    /**
     * 关闭指定的可关闭对象
     *
     * @param closeable 可关闭对象
     * @return 返回捕获的异常，如果需要关注，则对返回值进行判断
     */
    public static Throwable close(AutoCloseable closeable) {
        Throwable t = null;
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable e) {
                if (logger.isInfoEnabled()) {
                    String msg = "关闭closeable异常, close: %s, msg: %s";
                    logger.info(String.format(msg, closeable.getClass().getName(), e.getMessage()), e);
                }
                t = e;
            }
        }
        return t;
    }

    /**
     * 依次关闭所有的可关闭对象
     *
     * @param closeables 可关闭对象数组
     * @return 捕获的异常列表
     */
    public static List<Throwable> closeAll(AutoCloseable... closeables) {
        List<Throwable> list = new ArrayList<>();
        for (AutoCloseable closeable : closeables) {
            list.add(close(closeable));
        }
        return list;
    }
}

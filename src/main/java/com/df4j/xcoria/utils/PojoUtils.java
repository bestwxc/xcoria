package com.df4j.xcoria.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class PojoUtils {

    private static final Logger logger = LoggerFactory.getLogger(PojoUtils.class);

    /**
     * 比较两个对象是否相等
     *
     * @param o1 对象1
     * @param o2 对象2
     * @return 是否相等
     */
    public static boolean safeEquals(Object o1, Object o2) {
        if (o1 == null && o2 == null) {
            return true;
        }
        if (o1 == null || o2 == null) {
            return false;
        }
        if (!o1.getClass().equals(o2.getClass())) {
            return false;
        }
        return o1.equals(o2);
    }

    /**
     * 将obj转换为map
     *
     * @param o obj
     * @return 转换后的map
     */
    public static Map<String, Object> toMap(Object o) {
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = o.getClass();
        Field[] fields = clazz.getFields();
        for (Field f : fields) {
            try {
                f.setAccessible(true);
                Object v = f.get(o);
                map.put(f.getName(), v);
            } catch (Exception e) {
                logger.info("转换map时，出现错误，field:" + f.getName(), e);
            }
        }
        return map;
    }
}

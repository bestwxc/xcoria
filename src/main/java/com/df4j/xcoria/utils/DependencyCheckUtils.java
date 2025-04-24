package com.df4j.xcoria.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DependencyCheckUtils {

    private static final Logger logger = LoggerFactory.getLogger(DependencyCheckUtils.class);

    /**
     * 检查指定的依赖是否存在
     *
     * @param groupId    依赖的组ID
     * @param artifactId 依赖的包名
     * @param classNames 检查依赖包中间的类
     * @return 是否存在
     */
    public static boolean check(String groupId, String artifactId, String... classNames) {
        ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
        return check(classLoader, groupId, artifactId, classNames);
    }

    /**
     * 检查指定的依赖是否存在
     *
     * @param classLoader 类加载器
     * @param groupId     依赖的组ID
     * @param artifactId  依赖的包名
     * @param classNames  检查依赖包中间的类
     * @return 是否存在
     */
    public static boolean check(ClassLoader classLoader, String groupId, String artifactId, String... classNames) {
        boolean flag = ClassUtils.checkAllClassExists(classLoader, classNames);
        if (!flag) {
            logger.warn("部分类未找到，请在依赖中加入指定的依赖，groupId: {}, artifactId: {}", groupId, artifactId);
        }
        return flag;
    }
}

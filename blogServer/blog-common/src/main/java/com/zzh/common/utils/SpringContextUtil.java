package com.zzh.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author zzh
 * @description Spring contex工具类
 * @date 2022/4/23 11:40
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * 获取 Spring Bean
     * @param clazz 类
     * @param <T>   泛型
     * @return 对象
     */
    public static <T> T getBean(Class<T> clazz) {
        if (clazz == null) {
            return null;
        }
        return context.getBean(clazz);
    }

    /**
     * 获取 Spring Bean
     * @param bean 名称
     * @param <T>  泛型
     * @return 对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String bean) {
        if (bean == null) {
            return null;
        }
        return (T) context.getBean(bean);
    }

    /**
     * 获取 Spring Bean
     * @param beanName 名称
     * @param clazz    类
     * @param <T>      泛型
     * @return 对象
     */
    public static <T> T getBean(String beanName, Class<T> clazz) {
        if (null == beanName || "".equals(beanName.trim())) {
            return null;
        }
        if (clazz == null) {
            return null;
        }
        return (T) context.getBean(beanName, clazz);
    }

    /**
     * 获取上下文
     * @return 上下文
     */
    public static ApplicationContext getContext() {
        if (context == null) {
            throw new RuntimeException("There has no Spring ApplicationContext!");
        }
        return context;
    }
}

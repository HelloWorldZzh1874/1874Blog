package com.zzh.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zzh
 * @description 自定义job注解，@Retention用于标注定时任务类.运行时级别注解，通过反射获取信息;@Target(ElementType.TYPE)是一个类注解
 * @date 2022/4/9
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Job {
    String name() default "";
}

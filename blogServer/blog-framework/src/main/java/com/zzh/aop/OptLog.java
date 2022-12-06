package com.zzh.aop;

import java.lang.annotation.*;

/**
 * @Author zzh
 * @Date 2021/3/8 21:07
 * @Version 0.1
 * @Description 操作日志注解 操作日志注解,作用于方法(@Target),生命周期为运行时保留(@Retention)
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OptLog {
    /**
     * @return 操作类型
     */
    String optType() default "";
}

package com.zzh.common.utils;

import com.zzh.common.annotation.Job;
import org.reflections.Reflections;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zzh
 * @description TODO
 * @date 2022/4/915:15
 */
public class AnnotationUtil {

    /**
     * 通过Job注解的参数
     * @param packageName 包名
     * @return 返回参数set
     */
    public static Set<String> getInfoByAnnotation(String packageName){
        //反射工具包，指明扫描路径
        Reflections reflections = new Reflections(packageName);
        //获取带JobType注解的类
        Set<Class<?>> clazz = reflections.getTypesAnnotatedWith(Job.class);
        Set<String> clazzName = new HashSet<>();
        for(Class<?> aClazz : clazz){
            Job annotation = aClazz.getAnnotation(Job.class);
            clazzName.add(annotation.name());
        }
        return clazzName;
    }
}

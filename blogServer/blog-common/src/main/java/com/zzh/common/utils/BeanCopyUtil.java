package com.zzh.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zzh
 * @description bean复制相关工具
 * @date 2022/2/10 13:51
 */

public class BeanCopyUtil {
    /**
     * 根据现有对象的属性创建目标对象，并赋值
     * 注意：目标类和源类必须要有相同的属性，只匹配相同的属性名
     * @param source 需要复制的类
     * @param target 目标类
     * @return 返回结果对象
     */
    public static <T> T copyObject(Object source, Class<T> target) {
        T temp = null;
        try {
            temp = target.newInstance();
            if (null != source) {
                org.springframework.beans.BeanUtils.copyProperties(source, temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * 拷贝集合
     *
     */
    public static <T, S> List<T> copyList(List<S> source, Class<T> target) {
        List<T> list = new ArrayList<>();
        if (null != source && source.size() > 0) {
            for (Object obj : source) {
                list.add(BeanCopyUtil.copyObject(obj, target));
            }
        }
        return list;
    }
}

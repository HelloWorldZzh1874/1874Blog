package com.zzh.common.utils.oshi;

import cn.hutool.core.util.NumberUtil;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zzh
 * @description Oshi系统信息---内存
 * @date 2022/4/7 12:57
 */
@Data
public class Mem implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 内存总量
     */
    private double total;

    /**
     * 已用内存
     */
    private double used;

    /**
     * 剩余内存
     */
    private double free;

    public double getTotal() {
        return NumberUtil.div(total, (1024 * 1024 * 1024), 2);
    }

    public double getUsed() {
        return NumberUtil.div(used, (1024 * 1024 * 1024), 2);
    }


    public double getFree() {
        return NumberUtil.div(free, (1024 * 1024 * 1024), 2);
    }

    public double getUsage() {
        return NumberUtil.mul(NumberUtil.div(used, total, 4), 100);
    }
}

package com.zzh.common.constant;

/**
 * @author zzh
 * @description 定时任务相关常量
 * @date 2022/2/23 11:28
 */
public class QuartzConstant {
    /**
     * JOB包名
     */
    public static final String JOB_PACKAGE = "com.zzh.job";

    /**
     * Job 运行时状态
     */
    public static final Integer QUARTZ_RUN = 1;

    /**
     * Job 暂停状态
     */
    public static final Integer QUARTZ_PAUSE = 0;

    /**
     * 系统任务组0
     */
    public static final String SYSTEM_JOB = "SystemJob";

    /**
     * 普通任务组1
     */
    public static final String COMMON_JOB = "commonJob";
}

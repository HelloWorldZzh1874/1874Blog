package com.zzh.job.base;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author zzh
 * @description TODO
 * @date 2022/2/2123:33
 */
public interface BaseJob extends Job {
    /**
     * 基础任务接口，必须实现execute
     * @param context
     * @throws JobExecutionException
     */
    @Override
    void execute(JobExecutionContext context) throws JobExecutionException;
}

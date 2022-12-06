package com.zzh.service.impl;

import com.zzh.common.constant.HttpStatus;
import com.zzh.common.exception.CommonJobException;
import com.zzh.common.exception.QuartzJobWriteException;
import com.zzh.common.exception.baseException.CommonWriteException;
import com.zzh.common.utils.QuartzUtil;
import com.zzh.entity.SysQuartz;
import com.zzh.service.QuartzService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zzh
 * @description TODO
 * @date 2022/2/22 14:22
 */

@Service
public class QuartzServiceImpl implements QuartzService {
    /**
     * 定时任务工具
     */
    @Autowired
    private QuartzUtil quartzUtil;

    @Override
    public void addJob(SysQuartz sysQuartz) {
        // 如果参数不满足(创建一个定时任务必须要任务类名，组名和任务名)抛出异常
        if ("".equals(sysQuartz.getJobGroup()) || "".equals(sysQuartz.getJobName()) || "".equals(sysQuartz.getJobClassname()) || "".equals(sysQuartz.getCronExpression())) {
            throw new CommonWriteException(HttpStatus.BAD_REQUEST, "任务参数列表错误!");
        }
        try {
            // 调用工具类中的添加定时任务
            quartzUtil.addJob(sysQuartz);
        } catch (SchedulerException e) {
            throw new CommonWriteException(HttpStatus.ERROR, "任务调度器异常！");
        } catch (ReflectiveOperationException e) {
            throw new CommonWriteException(HttpStatus.ERROR, "创建任务异常，无法创建任务对象!");
        } catch (CommonJobException e) {
            throw new QuartzJobWriteException(e.getErrorCode(), e.getMessage());
        }
    }

    @Override
    public void pauseJob(Integer quartzId) {
        try {
            quartzUtil.pauseJob(quartzId);
        } catch (SchedulerException e) {
            throw new CommonWriteException(HttpStatus.ERROR, "任务调度器异常！");
        } catch (CommonJobException e) {
            throw new QuartzJobWriteException(e.getErrorCode(), e.getMessage());
        }
    }


    @Override
    public void resumeJob(Integer quartzId) {
        try {
            quartzUtil.resumeJob(quartzId);
        } catch (SchedulerException e) {
            throw new CommonWriteException(HttpStatus.ERROR, "任务调度器异常！");
        } catch (CommonJobException e) {
            throw new QuartzJobWriteException(e.getErrorCode(), e.getMessage());
        }
    }


    @Override
    public void deleteJob(Integer quartzId) {
        try {
            quartzUtil.deleteJob(quartzId);
        } catch (SchedulerException e) {
            throw new CommonWriteException(HttpStatus.ERROR, "任务调度器异常！");
        } catch (CommonJobException e) {
            throw new QuartzJobWriteException(e.getErrorCode(), e.getMessage());
        }
    }

    @Override
    public void modifyJob(SysQuartz sysQuartz) {
        try {
            quartzUtil.modifyJob(sysQuartz);
        } catch (SchedulerException e) {
            throw new CommonWriteException(HttpStatus.ERROR, "任务调度器异常！");
        }
    }

    @Override
    public void pauseAllJob() {
        try {
            quartzUtil.pauseAllJob();
        } catch (SchedulerException e) {
            throw new CommonWriteException(HttpStatus.ERROR, "任务调度器异常！");
        }
    }

    @Override
    public void resumeAllJob() {
        try {
            quartzUtil.resumeAllJob();
        } catch (SchedulerException e) {
            throw new CommonWriteException(HttpStatus.ERROR, "任务调度器异常！");
        }
    }
}

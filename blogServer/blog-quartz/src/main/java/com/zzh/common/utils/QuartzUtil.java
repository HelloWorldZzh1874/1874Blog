package com.zzh.common.utils;

import cn.hutool.core.date.DateUtil;
import com.zzh.common.constant.HttpStatus;
import com.zzh.common.exception.CommonJobException;
import com.zzh.common.exception.CornException;
import com.zzh.entity.LoginUser;
import com.zzh.entity.SysQuartz;
import com.zzh.job.base.BaseJob;
import com.zzh.service.SysQuartzService;
import com.zzh.utils.SecurityUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

import static com.zzh.common.constant.QuartzConstant.*;
import static org.quartz.DateBuilder.futureDate;

/**
 * @author zzh
 * @description 定时任务工具
 * @date 2022/2/2123:35
 */

@Component
public class QuartzUtil {

    @Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;

    @Autowired
    private DateUnit dateUnit;

    @Autowired
    SysQuartzService quartzService;


    /**
     * @param sysQuartz 系统任务
     * @throws SchedulerException           调度器异常
     * @throws ReflectiveOperationException 反射构造相关类的异常
     * @throws CornException                corn表达式异常
     * @description 根据任务参数选择添加普通时间任务还是corn任务
     */
    public void addJob(SysQuartz sysQuartz) throws SchedulerException, ReflectiveOperationException {
        // 如果即时任务的时间类型为空则判断为定时任务
        if (sysQuartz.getTimeType() == null) {
            // 判断corn表达式是否有效，无效抛出异常
            if (CornUtil.isValid(sysQuartz.getCronExpression())) {
                addCronJob(sysQuartz);
            } else {
                throw new CommonJobException(HttpStatus.PARMA_VALUE_ERROR, "Corn表达式不正确!");
            }
        } else {
            // 添加一个即时任务
            addSimpleJob(sysQuartz);
        }
    }

    /**
     * @param sysQuartz 系统任务
     * @throws SchedulerException           调度器异常
     * @throws ReflectiveOperationException 反射构造相关类的异常
     * @description 添加corn任务
     */
    private void addCronJob(SysQuartz sysQuartz) throws SchedulerException, ReflectiveOperationException {

        // 启动调度
        scheduler.start();

        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(getClass(sysQuartz.getJobClassname().trim()).getClass()).
                withIdentity(JOB_PACKAGE + "." + sysQuartz.getJobClassname(), sysQuartz.getJobGroup())
                .build();

        //表达式调度构建器(即任务执行的时间) corn表达式
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(sysQuartz.getCronExpression());
        //按新的cronExpression表达式构建一个新的trigger(触发器)
        CronTrigger trigger = TriggerBuilder.newTrigger().
                withIdentity(JOB_PACKAGE + "." + sysQuartz.getJobClassname(), sysQuartz.getJobGroup())
                .withSchedule(scheduleBuilder)
                .build();

        //传递参数
        if (sysQuartz.getInvokeParam() != null) {
            trigger.getJobDataMap().put("invokeParam", sysQuartz.getInvokeParam());
        }
        // job状态设置
        sysQuartz.setStatus(QUARTZ_RUN);
        sysQuartz.setCreateTime(DateUtil.date());
        LoginUser curUser = SecurityUtils.getCurUser();
        sysQuartz.setCreateBy(curUser.getUsername());
        // 将job信息添加数据库
        scheduler.scheduleJob(jobDetail, trigger);
        quartzService.addSysQuartz(sysQuartz);

    }

    /**
     * @param sysQuartz 系统任务
     * @throws SchedulerException           调度器异常
     * @throws ReflectiveOperationException 反射构造相关类的异常
     * @description 添加即时任务
     */
    private void addSimpleJob(SysQuartz sysQuartz) throws SchedulerException, ReflectiveOperationException {
        // 启动调度器
        scheduler.start();

        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(getClass(sysQuartz.getJobClassname().trim()).getClass())
                .withIdentity(sysQuartz.getJobGroup(), sysQuartz.getJobName())
                .build();

        // 解析时间类型(秒，分钟，小时，天...)
        DateBuilder.IntervalUnit verDate = dateUnit.verification(sysQuartz.getTimeType());
        // 设置触发器
        SimpleTrigger simpleTrigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                .withIdentity(JOB_PACKAGE + "." + sysQuartz.getJobClassname(), sysQuartz.getJobGroup())
                .startAt(futureDate(sysQuartz.getTime(), verDate))
                .forJob(JOB_PACKAGE + "." + sysQuartz.getJobClassname(), sysQuartz.getJobGroup())
                .build();

        //传递参数
        if (sysQuartz.getInvokeParam() != null) {
            simpleTrigger.getJobDataMap().put("invokeParam", sysQuartz.getInvokeParam());
        }
        // job状态设置为运行中
        sysQuartz.setStatus(QUARTZ_RUN);
        sysQuartz.setCreateTime(DateUtil.date());
        quartzService.addSysQuartz(sysQuartz);
        scheduler.scheduleJob(jobDetail, simpleTrigger);

    }

    /**
     * @param quartzId 要暂停的id 可以多选
     * @throws CommonJobException 暂停不存在的任务抛出
     * @throws SchedulerException 调度器异常
     * @description 暂停任务
     */
    public void pauseJob(Integer quartzId) throws SchedulerException {
        SysQuartz sysQuartz = null;
        //这里要先查询出任务信息,暂停需要任务名字和组名
        sysQuartz = quartzService.getById(quartzId);
        if (Objects.nonNull(sysQuartz)) {
            // 根据任务信息的类名和组名暂停任务
            jobPause(JOB_PACKAGE + "." + sysQuartz.getJobClassname(), sysQuartz.getJobGroup());
            // job状态设置为暂停
            sysQuartz.setStatus(QUARTZ_PAUSE);
            sysQuartz.setUpdateTime(DateUtil.date());
            // 更新数据库
            quartzService.updateById(sysQuartz);
        } else {
            throw new CommonJobException("暂停任务出错，该任务不存在!");
        }


    }

    /**
     * 暂停任务
     *
     * @param jobClassName 任务类名
     * @param jobGroupName 任务属于组
     * @throws SchedulerException 调度器异常
     */
    private void jobPause(String jobClassName, String jobGroupName) throws SchedulerException {
        scheduler.pauseJob(JobKey.jobKey(jobClassName, jobGroupName));
    }


    /**
     * @param quartzId 恢复job的id
     * @throws SchedulerException 调度器异常
     * @throws CommonJobException 任务不存在抛出
     * @description 恢复job
     */
    public void resumeJob(Integer quartzId) throws SchedulerException {
        SysQuartz sysQuartz = null;
        //这里要先查询出任务信息,暂停需要任务名字和组名
        sysQuartz = quartzService.getById(quartzId);
        if (Objects.nonNull(sysQuartz)) {
            jobResume(JOB_PACKAGE + "." + sysQuartz.getJobClassname(), sysQuartz.getJobGroup());
            // job状态设置为恢复运行
            sysQuartz.setStatus(QUARTZ_RUN);
            sysQuartz.setUpdateTime(DateUtil.date());
            // 更新数据库
            quartzService.updateById(sysQuartz);
        } else {
            throw new CommonJobException("恢复任务出错，该任务不存在!");
        }
    }

    /**
     * 恢复任务
     *
     * @param jobClassName 任务类名
     * @param jobGroupName 任务组
     * @throws SchedulerException 调度器异常
     */
    private void jobResume(String jobClassName, String jobGroupName) throws SchedulerException {
        scheduler.resumeJob(JobKey.jobKey(jobClassName, jobGroupName));
    }


    /**
     * @param quartzId 删除任务的id
     * @throws SchedulerException 调度器异常
     * @throws CommonJobException 任务不存在抛出
     * @description 删除任务
     */
    public void deleteJob(Integer quartzId) throws SchedulerException {
        SysQuartz sysQuartz = null;
        //这里要先查询出任务信息,暂停需要任务名字和组名
        sysQuartz = quartzService.getById(quartzId);
        if (Objects.nonNull(sysQuartz)) {
            jobDelete(JOB_PACKAGE + "." + sysQuartz.getJobClassname(), sysQuartz.getJobGroup());
            // 删除任务信息
            quartzService.removeById(sysQuartz);
        } else {
            throw new CommonJobException("删除任务出错，该任务不存在!");
        }
    }

    /**
     * 删除任务
     *
     * @param jobClassName 任务类名
     * @param jobGroupName 任务组
     * @throws SchedulerException 调度器异常
     */
    private void jobDelete(String jobClassName, String jobGroupName) throws SchedulerException {
        scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));
    }


    /**
     * @param sysQuartz 系统任务
     * @throws SchedulerException 调度器异常
     * @description 修改任务
     */
    public void modifyJob(SysQuartz sysQuartz) throws SchedulerException {
        modifyScheduleJob(JOB_PACKAGE + "." + sysQuartz.getJobClassname(), sysQuartz.getJobGroup(), sysQuartz.getCronExpression());
    }

    /**
     * @param jobClassName 任务类名
     * @param jobGroupName 任务组
     * @throws SchedulerException 调度器异常
     * @description 修改任务
     */
    private void modifyScheduleJob(String jobClassName, String jobGroupName, String cronExpression) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);
        // 表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        // 按新的cronExpression表达式重新构建trigger
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

        // 按新的trigger重新设置job执行
        scheduler.rescheduleJob(triggerKey, trigger);

    }


    /**
     * 暂停所有任务
     */
    public void pauseAllJob() throws SchedulerException {
        scheduler.pauseAll();
    }

    /**
     * 恢复所有任务
     */
    public void resumeAllJob() throws SchedulerException {
        scheduler.resumeAll();
    }

    /**
     * 根据类名称，通过反射得到该类，然后创建一个BaseJob的实例。
     * 由于自己的Job类都实现了BaseJob，
     * 所以这里不需要我们手动去判断。这里涉及到了一些java多态调用的机制
     *
     * @param classname 类名
     * @return 反射机制创建类
     */
    public static BaseJob getClass(String classname) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<?> class1 = Class.forName(JOB_PACKAGE + "." + classname);
        return (BaseJob) class1.newInstance();
    }
}

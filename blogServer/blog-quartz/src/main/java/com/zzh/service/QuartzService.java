package com.zzh.service;

import com.zzh.entity.SysQuartz;

/**
 * @author zzh
 * @description TODO
 * @date 2022/2/2216:31
 */
public interface QuartzService {

    /**
     * 添加一个任务
     *
     * @param sysQuartz 系统任务
     */
    void addJob(SysQuartz sysQuartz);

    /**
     * 暂停 job
     *
     * @param quartzId 暂停的job id
     * @date 2022/2/22
     */
    void pauseJob(Integer quartzId);

    /**
     * 恢复job执行
     *
     * @param quartzId 恢复的job id
     * @date 2022/2/22
     */
    void resumeJob(Integer quartzId);

    /**
     * 删除job
     *
     * @param quartzId 删除的job id
     * @date 2022/2/22
     */
    void deleteJob(Integer quartzId);

    /**
     * 修改job
     *
     * @param sysQuartz 系统任务实体
     * @date 2022/2/22
     */
    void modifyJob(SysQuartz sysQuartz);

    /**
     * 暂停所有
     */
    void pauseAllJob();

    /**
     * 恢复所有
     */
    void resumeAllJob();
}

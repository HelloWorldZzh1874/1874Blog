package com.zzh.job;

import com.zzh.common.annotation.Job;
import com.zzh.common.utils.RedisUtils;
import com.zzh.common.utils.SpringContextUtil;
import com.zzh.job.base.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.stereotype.Component;

import static com.zzh.common.constant.RedisConstant.IP_SET;

/**
 * @author zzh
 * @description 清除每日访问ip集合
 * @date 2022/4/19 23:05
 */

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
@Job(name = "ClearViewIpSet")
public class ClearViewIpSet implements BaseJob {

    private final RedisUtils redisUtils = SpringContextUtil.getBean(RedisUtils.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //清空redis中的ip
        redisUtils.remove(IP_SET);
    }
}

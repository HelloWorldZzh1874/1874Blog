package com.zzh.job;

import cn.hutool.core.date.DateUtil;
import com.zzh.common.OnlineUserMap;
import com.zzh.common.annotation.Job;
import com.zzh.common.utils.SpringContextUtil;
import com.zzh.job.base.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.stereotype.Component;

/**
 * @author zzh
 * @description TODO
 * @date 2022/5/1615:44
 */

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
@Job(name = "LogoutUser")
public class LogoutUser implements BaseJob {

    private final OnlineUserMap onlineUserMap = SpringContextUtil.getBean(OnlineUserMap.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        onlineUserMap.forEach((key,value)->{
            if(DateUtil.compare(DateUtil.date(),value.getLogoutDate())>=0){
                onlineUserMap.remove(key);
            }
        });
    }
}

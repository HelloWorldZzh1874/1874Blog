package com.zzh.job;

import cn.hutool.core.date.DateUtil;
import com.zzh.common.annotation.Job;
import com.zzh.common.utils.RedisUtils;
import com.zzh.common.utils.SpringContextUtil;
import com.zzh.entity.UniqueView;
import com.zzh.job.base.BaseJob;
import com.zzh.mapper.UniqueViewMapper;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.stereotype.Component;

import static com.zzh.common.constant.RedisConstant.IP_SET;

/**
 * @author zzh
 * @description TODO
 * @date 2022/4/1923:07
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
@Job(name = "SaveUniqueView")
public class SaveUniqueView implements BaseJob {

    private final RedisUtils redisUtils = SpringContextUtil.getBean(RedisUtils.class);
    private final UniqueViewMapper uniqueViewMapper = SpringContextUtil.getBean(UniqueViewMapper.class);

    @Override
    public void execute(JobExecutionContext context) {
        // 获取每天用户量
        long count = redisUtils.getMembers(IP_SET).size();
        // 获取昨天日期插入数据
        UniqueView uniqueView = UniqueView.builder()
                .createTime(DateUtil.yesterday())
                .viewsCount((int) count).build();
        uniqueViewMapper.insert(uniqueView);
    }
}

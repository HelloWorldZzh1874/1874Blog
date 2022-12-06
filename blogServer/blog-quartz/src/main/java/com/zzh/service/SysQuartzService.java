package com.zzh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zzh.dto.QuartzInfoDto;
import com.zzh.entity.SysQuartz;
import com.zzh.vo.ConditionVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzh
 * @since 2022-02-22
 */
public interface SysQuartzService extends IService<SysQuartz> {
    /**
     * 添加任务信息
     * @param sqQuartzSys 系统任务
     */
    void addSysQuartz(SysQuartz sqQuartzSys);


    /**
     * 查询所有定时任务以及相关信息
     * @param conditionVO 分页信息
     * @return 定时任务以及任务类名，固定组名
     */
    QuartzInfoDto getAllQuartzInfo(ConditionVO conditionVO);
}

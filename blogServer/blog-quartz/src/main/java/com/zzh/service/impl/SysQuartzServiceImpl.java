package com.zzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzh.common.utils.AnnotationUtil;
import com.zzh.dto.QuartzDto;
import com.zzh.dto.QuartzInfoDto;
import com.zzh.entity.SysQuartz;
import com.zzh.mapper.SysQuartzMapper;
import com.zzh.service.SysQuartzService;
import com.zzh.vo.ConditionVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.zzh.common.constant.QuartzConstant.JOB_PACKAGE;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zzh
 * @since 2022-02-22
 */
@Service
public class SysQuartzServiceImpl extends ServiceImpl<SysQuartzMapper, SysQuartz> implements SysQuartzService {

    @Autowired
    SysQuartzMapper sysQuartzMapper;

    @Override
    public void addSysQuartz(SysQuartz sqQuartzSys) {
        sysQuartzMapper.insert(sqQuartzSys);
    }

    @Override
    public QuartzInfoDto getAllQuartzInfo(ConditionVO conditionVO) {
        // 包装任务类名给前台
        Set<String> quartzClazzName = AnnotationUtil.getInfoByAnnotation(JOB_PACKAGE);
        PageHelper.startPage(conditionVO.getCurrent(), conditionVO.getSize());
        List<SysQuartz> sysQuartzList = this.list(new LambdaQueryWrapper<SysQuartz>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), SysQuartz::getJobName, conditionVO.getKeywords())
        );
        PageInfo<SysQuartz> pageInfoDto = new PageInfo<>(sysQuartzList);
        List<QuartzDto> quartzDtoList = sysQuartzList.stream().map(item -> QuartzDto.builder().id(item.getId())
                .jobName(item.getJobName())
                .jobClassname(item.getJobClassname())
                .jobGroup(item.getJobGroup())
                .cronExpression(item.getCronExpression())
                .status(item.getStatus())
                .createBy(item.getCreateBy())
                .createTime(item.getCreateTime())
                .updateTime(item.getUpdateTime()).build()).collect(Collectors.toList());
        QuartzInfoDto result = new QuartzInfoDto();
        result.setJobClazzNameList(quartzClazzName);
        result.setQuartzDtoList(quartzDtoList);
        result.setTotal(pageInfoDto.getTotal());
        result.setSize(pageInfoDto.getSize());
        return result;
    }
}

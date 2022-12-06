package com.zzh.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.zzh.common.constant.QuartzConstant.COMMON_JOB;
import static com.zzh.common.constant.QuartzConstant.SYSTEM_JOB;

/**
 * @author zzh
 * @description TODO
 * @date 2022/4/913:24
 */
@Data
public class QuartzInfoDto {
    /**
     * 任务列表
     */
    List<QuartzDto> quartzDtoList;

    /**
     * 任务类列表
     */
    Set<String> jobClazzNameList;

    /**
     * 任务组列表
     */
    Set<String> jobGroups;

    /**
     * 任务总数
     */
    Long total;

    /**
     * 每页个数
     */
    Integer size;

    /**
     * 初始化默认任务组
     */
    public QuartzInfoDto() {
        jobGroups = new HashSet<>();
        jobGroups.add(SYSTEM_JOB);
        jobGroups.add(COMMON_JOB);
    }
}

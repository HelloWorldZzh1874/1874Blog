package com.zzh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zzh
 * @description TODO
 * @date 2022/4/913:12
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuartzDto {
    /**
     * 任务id
     */
    private Integer id;

    /**
     * 任务名
     */
    private String jobName;

    /**
     * 任务类名
     */
    private String jobClassname;

    /**
     * 任务组
     */
    private String jobGroup;

    /**
     * corn表达式
     */
    private String cronExpression;

    /**
     * 任务状态
     */
    private Integer status;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}

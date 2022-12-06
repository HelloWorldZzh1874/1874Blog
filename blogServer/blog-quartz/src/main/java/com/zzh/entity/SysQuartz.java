package com.zzh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统任务实体
 * </p>
 *
 * @author zzh
 * @since 2022-02-22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysQuartz对象", description = "系统任务")
public class SysQuartz implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "任务名")
    private String jobName;

    @ApiModelProperty(value = "任务类名")
    private String jobClassname;

    @ApiModelProperty(value = "任务组")
    private String jobGroup;

    @ApiModelProperty(value = "即时任务时间类型 1-年，2-月....")
    private Integer timeType;

    @ApiModelProperty(value = "即时任务时间")
    private Integer time;

    @ApiModelProperty(value = "定时任务 corn表达式，与时间二选一")
    private String cronExpression;

    @ApiModelProperty(value = "任务所需参数")
    private String invokeParam;

    @ApiModelProperty(value = "任务状态0暂停1运行中")
    private Integer status;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}

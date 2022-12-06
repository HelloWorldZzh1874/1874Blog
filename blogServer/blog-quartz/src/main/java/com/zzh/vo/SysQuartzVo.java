package com.zzh.vo;

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
public class SysQuartzVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务名")
    private String jobName;

    @ApiModelProperty(value = "任务类名")
    private String jobClassname;

    @ApiModelProperty(value = "任务组,系统任务，普通任务")
    private String jobGroup;

    @ApiModelProperty(value = "定时任务 corn表达式，与时间二选一")
    private String cronExpression;

    @ApiModelProperty(value = "初始化状态0暂停1开始")
    private Integer jobStatus;
}

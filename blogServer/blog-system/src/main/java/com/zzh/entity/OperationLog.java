package com.zzh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author zzh
 * @since 2021-08-02
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_operation_log")
@ApiModel(value="OperationLog对象", description="")
public class OperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "操作模块")
    private String optModule;

    @ApiModelProperty(value = "操作类型")
    private String optType;

    @ApiModelProperty(value = "操作url")
    private String optUrl;

    @ApiModelProperty(value = "操作方法")
    private String optMethod;

    @ApiModelProperty(value = "操作描述")
    private String optDesc;

    @ApiModelProperty(value = "请求参数")
    private String requestParam;

    @ApiModelProperty(value = "请求方式")
    private String requestMethod;

    @ApiModelProperty(value = "返回数据")
    private String responseData;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "用户昵称")
    private String username;

    @ApiModelProperty(value = "操作ip")
    private String ipAddr;

    @ApiModelProperty(value = "操作地址")
    private String ipSource;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}

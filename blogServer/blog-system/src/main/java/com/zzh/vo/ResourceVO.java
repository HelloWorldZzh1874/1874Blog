package com.zzh.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @Author zzh
 * @Date 2021/7/31 16:20
 * @Version 0.1
 * @Description 前端资源信息
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResourceVO {
    /**
     * 资源id
     */
    @ApiModelProperty(name = "id", value = "资源id", required = true, dataType = "Integer")
    private Integer id;

    /**
     * 资源名
     */
    @NotBlank(message = "资源名不能为空")
    @ApiModelProperty(name = "resourceName", value = "资源名", required = true, dataType = "String")
    private String resourceName;

    /**
     * 路径
     */
    @ApiModelProperty(name = "url", value = "资源路径", required = true, dataType = "String")
    private String url;

    /**
     * 请求方式
     */
    @ApiModelProperty(name = "url", value = "资源路径", required = true, dataType = "String")
    private String requestMethod;

    /**
     * 父资源id
     */
    @ApiModelProperty(name = "parentId", value = "父资源id", required = true, dataType = "Integer")
    private Integer parentId;

    /**
     * 是否禁用
     */
    @ApiModelProperty(name = "isDisable", value = "是否禁用", required = true, dataType = "Integer")
    private Integer isDisable;

    /**
     * 是否匿名访问
     */
    @ApiModelProperty(name = "isAnonymous", value = "是否匿名访问", required = true, dataType = "Integer")
    private Integer isAnonymous;
}


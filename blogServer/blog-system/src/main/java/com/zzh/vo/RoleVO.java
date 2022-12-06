package com.zzh.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @Author zzh
 * @Date 2021/7/31 16:35
 * @Version 0.1
 * @Description TODO
 **/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "角色")
public class RoleVO {
    /**
     * id
     */
    @ApiModelProperty(name = "id", value = "用户id", dataType = "Integer")
    private Integer id;

    /**
     * 标签名
     */
    @NotBlank(message = "角色名不能为空")
    @ApiModelProperty(name = "roleName", value = "标签名", required = true, dataType = "String")
    private String roleName;

    /**
     * 标签名
     */
    @NotBlank(message = "权限标签不能为空")
    @ApiModelProperty(name = "roleLabel", value = "标签名", required = true, dataType = "String")
    private String roleLabel;

    /**
     * 资源列表
     */
    @ApiModelProperty(name = "resourceIdList", value = "资源列表", required = true, dataType = "List<Integer>")
    private List<Integer> resourceIdList;

    /**
     * 菜单列表
     */
    @ApiModelProperty(name = "menuIdList", value = "菜单列表", required = true, dataType = "List<Integer>")
    private List<Integer> menuIdList;

    /**
     * 操作类型 1修改菜单 2修改资源
     */
    @ApiModelProperty(name = "type", value = "操作类型", required = false, dataType = "Integer")
    private Integer type;
}

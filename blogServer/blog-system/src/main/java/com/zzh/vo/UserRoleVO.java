package com.zzh.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author zzh
 * @Date 2021/7/30 17:07
 * @Version 0.1
 * @Description 后台修改用户权限及名称
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "用户权限")
public class UserRoleVO {

    /**
     * 用户id
     */
    @NotNull(message = "id不能为空")
    @ApiModelProperty(name = "id", value = "用户id", dataType = "Integer")
    private Long id;

    /**
     * 用户信息id
     */
    @NotNull(message = "id不能为空")
    @ApiModelProperty(name = "userInfoId", value = "用户信息id", dataType = "Integer")
    private Long userInfoId;

    /**
     * 用户昵称
     */
    @NotBlank(message = "昵称不能为空")
    @ApiModelProperty(name = "nickname", value = "昵称", dataType = "String")
    private String nickname;

    /**
     * 用户角色
     */
    @NotNull(message = "用户角色不能为空")
    @ApiModelProperty(name = "roleName", value = "角色", dataType = "String")
    private String roleName;
}

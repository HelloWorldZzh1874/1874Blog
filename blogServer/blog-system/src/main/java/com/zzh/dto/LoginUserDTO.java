package com.zzh.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author zzh
 * @description 返回给前端的用户(User)数据
 * @date 2022/3/5 12:54
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "登录用户返回数据", description = "")
public class LoginUserDTO {

    @ApiModelProperty(value = "用户id")
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "用户头像地址")
    private String avatar;

    @ApiModelProperty(value = "个人地址")
    private String website;

    @ApiModelProperty(value = "签名")
    private String intro;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "登录成功后的token")
    private String token;

    @ApiModelProperty(value = "点赞文章")
    Set<Integer> articleLikeSet;

    @ApiModelProperty(value = "点赞评论")
    Set<Integer> commentLikeSet;
}

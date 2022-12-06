package com.zzh.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 *  User
 * </p>
 *
 * @author zzh
 * @since 2022-03-04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("tb_user")
@ApiModel(value="User对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = " id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户信息id")
    private Long infoId;

    @ApiModelProperty(value = "登陆账号")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "账号是否可用(1可用 0不可用)")
    private Boolean enabled;

    @ApiModelProperty(value = "账号是否锁定(1锁定)")
    private Boolean notLock;

    @ApiModelProperty(value = "上次登陆时间")
    private Date lastLogin;

    @ApiModelProperty(value = "上次登录ip")
    private String ipAddr;

    @ApiModelProperty(value = "ip源")
    private String ipSource;

    @ApiModelProperty(value = "浏览器")
    private String browser;

    @ApiModelProperty(value = "系统")
    private String os;
}

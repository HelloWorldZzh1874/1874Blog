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
 *
 * </p>
 *
 * @author zzh
 * @since 2022-03-30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("tb_friend_link")
@ApiModel(value="FriendLink对象", description="")
public class FriendLink implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "链接名")
    private String linkName;

    @ApiModelProperty(value = "链接头像")
    private String linkAvatar;

    @ApiModelProperty(value = "链接地址")
    private String linkAddress;

    @ApiModelProperty(value = "链接介绍")
    private String linkIntro;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}

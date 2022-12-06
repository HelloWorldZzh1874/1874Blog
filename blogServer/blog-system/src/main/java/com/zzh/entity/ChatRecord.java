package com.zzh.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author zzh
 * @since 2022-04-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_chat_record")
@ApiModel(value="ChatRecord对象", description="")
public class ChatRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "聊天内容")
    private String content;

    @ApiModelProperty(value = "ip地址")
    private String ipAddr;

    @ApiModelProperty(value = "ip来源")
    private String ipSource;

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}

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
 * @since 2022-03-13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("tb_comment")
@ApiModel(value="Comment对象", description="")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "评论用户Id")
    private Long userId;

    @ApiModelProperty(value = "评论文章id")
    private Integer articleId;

    @ApiModelProperty(value = "评论内容")
    private String commentContent;

    @ApiModelProperty(value = "评论时间")
    private Date createTime;

    @ApiModelProperty(value = "回复用户id")
    private Long replyId;

    @ApiModelProperty(value = "父评论id")
    private Integer parentId;

    @ApiModelProperty(value = "是否已读  0否 1是")
    private Integer isRead;
}

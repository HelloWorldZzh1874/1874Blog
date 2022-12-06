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
 * @since 2022-03-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("tb_article")
@ApiModel(value="Article对象", description="")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "作者")
    private Long userId;

    @ApiModelProperty(value = "文章分类")
    private Integer categoryId;

    @ApiModelProperty(value = "文章缩略图")
    private String articleCover;

    @ApiModelProperty(value = "标题")
    private String articleTitle;

    @ApiModelProperty(value = "内容")
    private String articleContent;

    @ApiModelProperty(value = "发表时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "是否置顶 0否 1是")
    private Integer isTop;

    @ApiModelProperty(value = "是否为草稿 0否 1是")
    private Integer isDraft;

    @ApiModelProperty(value = "是否删除  0否 1是")
    private Integer isDelete;


}

package com.zzh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
@ApiModel(value="ConArticleTag对象", description="")
public class ConArticleTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "文章id")
    private Long articleId;

    @ApiModelProperty(value = "标签id")
    private Integer tagId;


}

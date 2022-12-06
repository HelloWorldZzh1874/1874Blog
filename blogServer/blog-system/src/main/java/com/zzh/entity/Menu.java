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
 * Menu对象
 * </p>
 *
 * @author zzh
 * @since 2022-03-06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("tb_menu")
@ApiModel(value="Menu对象", description="")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "菜单名")
    private String name;

    @ApiModelProperty(value = "菜单路径")
    private String path;

    @ApiModelProperty(value = "组件")
    private String component;

    @ApiModelProperty(value = "菜单icon")
    private String icon;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "排序")
    private Integer orderNum;

    @ApiModelProperty(value = "父id")
    private Integer parentId;

    @ApiModelProperty(value = "是否禁用 0否1是")
    private Integer isDisable;

    @ApiModelProperty(value = "是否隐藏  0否1是")
    private Integer isHidden;


}

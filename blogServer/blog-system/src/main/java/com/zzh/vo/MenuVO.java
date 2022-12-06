package com.zzh.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @Author zzh
 * @Date 2021/8/1 16:49
 * @Version 0.1
 * @Description TODO
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuVO {
    /**
     * id
     */
    private Integer id;
    /**
     * 菜单名
     */
    @NotBlank(message = "菜单名不能为空")
    private String name;
    /**
     * 组件
     */
    @NotBlank(message = "组件不能为空")
    private String component;
    /**
     * 路径
     */
    @NotBlank(message = "路径不能为空")
    private String path;
    /**
     * icon图标
     */
    private String icon;
    /**
     * 父菜单id
     */
    private Integer parentId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 排序
     */
    private Integer orderNum;
    /**
     * 是否隐藏
     */
    private Integer isHidden;
    /**
     * 是否关闭
     */
    private Integer isDisable;
}

package com.zzh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zzh
 * @description 菜单封装
 * @date 2022/2/10 12:47
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuDTO {
    /**
     * 菜单名
     */
    private String name;

    /**
     * url
     */
    private String path;

    /**
     * 组件
     */
    private String component;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 是否隐藏
     */
    private Integer isHidden;

    /**
     * 子菜单
     */
    private List<MenuDTO> children;
}

package com.zzh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author zzh
 * @description 角色权限DTO
 * @date 2022/2/3 12:31
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleUrlDTO {

    /**
     * 资源id
     */
    private Integer resId;

    /**
     * 资源名
     */
    private String resName;

    /**
     * 角色名
     */
    private Set<String> roleList;

    /**
     * 资源路径
     */
    private String url;

    /**
     * 资源请求方式
     */
    private String requestMethod;

    /**
     * 是否匿名 1匿名
     */
    private Integer isAnonymous;
}

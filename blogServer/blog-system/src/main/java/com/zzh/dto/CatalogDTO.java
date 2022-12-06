package com.zzh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author zzh
 * @Date 2021/7/28 21:00
 * @Version 0.1
 * @Description 文章分类数据类型
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CatalogDTO {

    /**
     * 分类id
     */
    private Integer id;
    /**
     * 分类名
     */
    private String categoryName;
    /**
     * 分类下的文章数量
     */
    private Integer articleCount;
}

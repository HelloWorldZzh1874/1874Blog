package com.zzh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author zzh
 * @Date 2021/8/8 21:16
 * @Version 0.1
 * @Description 文章上下篇DTO
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticlePaginationDTO {
    /**
     * id
     */
    private Long id;

    /**
     * 文章缩略图
     */
    private String articleCover;

    /**
     * 标题
     */
    private String articleTitle;

}

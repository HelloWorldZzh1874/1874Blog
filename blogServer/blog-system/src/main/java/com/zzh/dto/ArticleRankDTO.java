package com.zzh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author zzh
 * @Date 2021/8/4 15:26
 * @Version 0.1
 * @Description 文章浏览量DTO
 **/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRankDTO {

    /**
     * 标题
     */
    private String articleTitle;

    /**
     * 浏览量
     */
    private Integer viewsCount;
}

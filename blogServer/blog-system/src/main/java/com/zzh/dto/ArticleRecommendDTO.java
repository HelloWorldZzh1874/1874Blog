package com.zzh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author zzh
 * @Date 2021/8/8 21:17
 * @Version 0.1
 * @Description 推荐文章
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleRecommendDTO {
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

    /**
     * 创建时间
     */
    private Date createTime;

}

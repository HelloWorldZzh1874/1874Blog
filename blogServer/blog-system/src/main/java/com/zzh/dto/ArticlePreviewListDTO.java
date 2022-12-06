package com.zzh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author zzh
 * @Date 2021/8/7 22:54
 * @Version 0.1
 * @Description 分类或标签下的文章列表 根据调教查询
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticlePreviewListDTO {
    /**
     * 条件对应的文章列表
     */
    private List<ArticlePreviewDTO> articlePreviewDTOList;

    /**
     * 条件名
     */
    private String name;
}

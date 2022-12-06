package com.zzh.dto;

import com.zzh.entity.Category;
import com.zzh.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author zzh
 * @Date 2021/7/26 13:03
 * @Version 0.1
 * @Description 文章选项返回类型
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleOptionDTO {

    private List<Tag> tagList;
    private List<Category> categoryList;

}

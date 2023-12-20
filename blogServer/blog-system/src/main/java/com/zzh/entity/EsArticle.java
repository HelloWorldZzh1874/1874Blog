package com.zzh.entity;

import com.zzh.common.base.EsEntity;
import io.swagger.models.auth.In;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


/**
 * @author zzh
 * @description TODO
 * @date 2022/4/2315:00
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(indexName = "article_index")
public class EsArticle extends EsEntity {

    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String articleTitle;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String articleContent;

    @Field(type = FieldType.Integer)
    private Integer isDraft;

    @Field(type = FieldType.Integer)
    private Integer isLogicDel;
}

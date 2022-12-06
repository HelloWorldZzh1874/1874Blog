package com.zzh.mapper;

import com.zzh.entity.EsArticle;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author zzh
 * @description Es搜索m
 * @date 2022/4/23
 * @return
 */
@Mapper
public interface EsArticleMapper extends ElasticsearchRepository<EsArticle,Long> {
}

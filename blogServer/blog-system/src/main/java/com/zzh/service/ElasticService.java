package com.zzh.service;

import com.zzh.common.base.EsEntity;
import com.zzh.vo.ConditionVO;

import java.util.List;

/**
 * @author 37436
 * @description elastic相关业务
 */
public interface ElasticService<T extends EsEntity>{
    /**
     * 保存实体对象到es
     * @param entity 单个实体对象实体对象
     */
    void saveEntity(T entity);

    /**
     * 批量保存实体对象到es
     * @param list 实体对象列表
     */
    void saveBatch(List<T> list);

    /**
     * 根据条件高亮查询
     * @param conditionVO 查询条件内容
     * @return 返回list
     */
    List<T> searchHighlights(ConditionVO conditionVO);

    /**
     * 根据id查询
     * @param id id
     * @return 返回实体对象
     */
    T searchById(Long id);

    /**
     * 删除
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 查询所有
     */
    List<T> findAll();

    /**
     * 將數據庫的文章數據同步到es中
     */
    void initArticleData();
}

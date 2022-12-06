package com.zzh.mapper;

import com.zzh.dto.CatalogDTO;
import com.zzh.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zzh
 * @since 2022-03-09
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    /**
     * 查询分类和对应文章数量
     * @return 分类集合
     */
    List<CatalogDTO> listCategoryDTO();
}

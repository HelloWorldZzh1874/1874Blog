package com.zzh.service;

import com.github.pagehelper.PageInfo;
import com.zzh.dto.CatalogDTO;
import com.zzh.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzh.vo.CatalogVO;
import com.zzh.vo.ConditionVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzh
 * @since 2022-03-09
 */
public interface CategoryService extends IService<Category> {
    /**
     * 查询后台分类列表
     * @date 2022/3/13
     * @param conditionVO 查询条件
     * @return 返回分页内容
     */
    PageInfo<Category> getBackCatalogDto(ConditionVO conditionVO);

    /**
     * 根据id列表删除分类
     * @date 2022/3/13
     * @param categoryIdList id列表
     */
    void deleteCategory(List<Integer> categoryIdList);

    /**
     * 根据条件更改或添加分类
     * @date 2022/3/13
     * @param catalogVo 根据条件更改或添加分类
     */
    void saveOrUpdateCategory(CatalogVO catalogVo);

    /**
     * 查询分类列表
     * @return
     */
    PageInfo<CatalogDTO> getCatalogList();
}

package com.zzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzh.common.constant.HttpStatus;
import com.zzh.common.exception.baseException.CommonWriteException;
import com.zzh.common.exception.baseException.MyException;
import com.zzh.dto.CatalogDTO;
import com.zzh.entity.Article;
import com.zzh.entity.Category;
import com.zzh.mapper.ArticleMapper;
import com.zzh.mapper.CategoryMapper;
import com.zzh.service.CategoryService;
import com.zzh.vo.CatalogVO;
import com.zzh.vo.ConditionVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzh
 * @since 2022-03-09
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    ArticleMapper articleMapper;
    @Override
    public PageInfo<Category> getBackCatalogDto(ConditionVO conditionVO) {
        // 设置分页信息
        PageHelper.startPage(conditionVO.getCurrent(), conditionVO.getSize());
        List<Category> categoryList = categoryMapper.selectList(new LambdaQueryWrapper<Category>()
                .select(Category::getId, Category::getCategoryName, Category::getCreateTime)
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), Category::getCategoryName, conditionVO.getKeywords())
                .orderByDesc(Category::getId));
        return new PageInfo<>(categoryList);
    }

    @Override
    public void deleteCategory(List<Integer> categoryIdList) {
        // 查询分类id下是否有文章
        Integer count = articleMapper.selectCount(new LambdaQueryWrapper<Article>()
                .in(Article::getCategoryId, categoryIdList));
        if (count > 0) {
            // 如果该分类下有文章，则不删除
            throw new CommonWriteException("删除失败！该分类下有文章!请先删除相关文章");
        }
        categoryMapper.deleteBatchIds(categoryIdList);
    }

    @Override
    public void saveOrUpdateCategory(CatalogVO catalogVo) {
        Integer count = categoryMapper.selectCount(new LambdaQueryWrapper<Category>()
                .eq(Category::getCategoryName, catalogVo.getCategoryName()));
        if(count>0){
            throw new CommonWriteException(HttpStatus.NOT_MODIFIED,"保存失败,分类名重复!");
        }
        // 生成更新或添加对象
        Category category =Category.builder()
                .id(catalogVo.getId())
                .categoryName(catalogVo.getCategoryName())
                .createTime(Objects.isNull(catalogVo.getId())? new Date() :null)
                .build();
        this.saveOrUpdate(category);
    }

    @Override
    public PageInfo<CatalogDTO> getCatalogList() {
        return new PageInfo<>(categoryMapper.listCategoryDTO());
    }
}

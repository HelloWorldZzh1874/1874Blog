package com.zzh.controller;


import com.zzh.aop.OptLog;
import com.zzh.common.base.Result;
import com.zzh.common.exception.baseException.CommonWriteException;
import com.zzh.service.CategoryService;
import com.zzh.vo.CatalogVO;
import com.zzh.vo.ConditionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.zzh.common.base.BaseController;

import java.util.List;

import static com.zzh.aop.OptTypeConst.REMOVE;
import static com.zzh.aop.OptTypeConst.SAVE_OR_UPDATE;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zzh
 * @since 2022-03-09
 */
@Api(tags ="分类模块")
@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController {

    @Autowired
    CategoryService categoryService;

    /**
     * @description 查询分类信息
     * @date 2022/3/13
     * @param conditionVO 分页查询信息
     * @return com.zzh.common.base.Result
     */
    @ApiOperation(value = "查询分类信息")
    @PostMapping("/admin/categories")
    public Result listCategories(ConditionVO conditionVO) {
        return Result.success(categoryService.getBackCatalogDto(conditionVO));
    }

    /**
     * @description 删除分类
     * @date 2022/3/13
     * @param categoryIdList 删除分类的id列表
     * @return com.zzh.common.base.Result
     */
    @ApiOperation(value = "删除分类")
    @OptLog(optType = REMOVE)
    @DeleteMapping("/admin/deleteCategory")
    public Result deleteCategory(@RequestBody List<Integer> categoryIdList) {
        categoryService.deleteCategory(categoryIdList);
        return Result.success("操作成功");
    }

    /**
     * @description 根据条件更改或添加分类
     * @date 2022/3/13
     * @param catalogVo 修改或添加的信息
     * @return com.zzh.common.base.Result
     */
    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "添加或修改分类")
    @PostMapping("/admin/saveOrUpdateCategory")
    public Result saveOrUpdateCategory(@Validated @RequestBody CatalogVO catalogVo) {
        categoryService.saveOrUpdateCategory(catalogVo);
        return Result.success();
    }

    /**
     * @description 查看文章分类
     * @date 2022/4/20
     * @param
     * @return com.zzh.common.base.Result
     */
    @ApiOperation(value = "查看文章分类")
    @GetMapping("/categoryList")
    public Result getCatalogList(){
        return Result.success(categoryService.getCatalogList());
    }
}


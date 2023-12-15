package com.zzh.controller;

import com.zzh.common.base.BaseController;
import com.zzh.common.base.Result;
import com.zzh.entity.EsArticle;
import com.zzh.service.impl.EsArticleServiceImpl;
import com.zzh.vo.ConditionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 37436
 */
@RestController
@RequestMapping("/EsSearch")
public class EsSearchController extends BaseController {

    @Autowired
    private EsArticleServiceImpl esArticleService;

    @GetMapping("/esFindAll")
    public Result findAll(){
        return Result.success(esArticleService.findAll());
    }

    @GetMapping("/esFindById")
    public Result findById(Long id){
        return Result.success(esArticleService.searchById(id));
    }

    @PostMapping("/esSaveOne")
    public Result saveOne(EsArticle esArticle){
        esArticleService.saveEntity(esArticle);
        return Result.success();
    }

    @PostMapping("esSaveBatch")
    public Result saveBatch(List<EsArticle> list){
        esArticleService.saveBatch(list);
        return Result.success();
    }

    @DeleteMapping("/esDeleteById")
    public Result deleteById(Long id){
        esArticleService.deleteById(id);
        return Result.success();
    }

    @PostMapping("/searchHighlight")
    public Result searchHighlight(ConditionVO conditionVO){
        return Result.success(esArticleService.searchHighlights(conditionVO));
    }

    @GetMapping("/initArticleData")
    public Result initArticleData(){
        esArticleService.initArticleData();
        return Result.success();
    }
}

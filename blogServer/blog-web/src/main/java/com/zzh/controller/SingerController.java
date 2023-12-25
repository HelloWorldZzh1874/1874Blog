package com.zzh.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zzh.aop.OptLog;
import com.zzh.common.base.BaseController;
import com.zzh.common.base.Result;
import com.zzh.entity.Music;
import com.zzh.service.MusicService;
import com.zzh.service.SingerService;
import com.zzh.vo.ConditionVO;
import com.zzh.vo.SingerVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static com.zzh.aop.OptTypeConst.REMOVE;
import static com.zzh.aop.OptTypeConst.SAVE_OR_UPDATE;

/**
 * <p>
 *  歌手 前端控制器
 * </p>
 *
 * @author zzh
 * @since 2022-04-02
 */
@RestController
@RequestMapping("/singer")
@Api(tags = "歌手模块")
public class SingerController extends BaseController {

    @Autowired
    private SingerService singerService;

    @Autowired
    private MusicService musicService;

    /**
     * @description 查询歌手列表
     * @date 2022/4/2
     * @param conditionVO
     * @return com.zzh.common.base.Result
     */
    @ApiOperation(value = "查询歌手")
    @PostMapping("/admin/list")
    public Result listSingers(ConditionVO conditionVO) {
        return Result.success(singerService.listSingersPage(conditionVO));
    }

    /**
     * @description 查询歌手搜索对象(简略信息)
     * @date 2022/4/5
     * @return com.zzh.common.base.Result
     */
    @ApiOperation("查询歌手搜索对象")
    @GetMapping("/admin/findAll")
    public Result listAllSingers() {
        return Result.success(singerService.listSingers());
    }


    /**
     * @description 更新图片
     * @date 2022/4/2
     * @param file 图片文件
     * @param id 更新歌手id
     * @return com.zzh.common.base.Result
     */
    @ApiOperation("更新图片")
    @PostMapping("/admin/updateImg")
    public Result updateImg(MultipartFile file, Integer id){
        singerService.updateImg(file,id);
        return Result.success();
    }

    /**
     * @description 删除歌手信息
     * @date 2022/4/5
     * @param id
     * @return com.zzh.common.base.Result
     */
    @ApiOperation(value = "删除歌手信息")
    @OptLog(optType = REMOVE)
    @DeleteMapping("/admin/delete")
    public Result deleteSinger(@RequestParam Integer id) {
        if(!musicService.list(new LambdaQueryWrapper<Music>().eq(Music::getSingerId, id)).isEmpty()){
            return Result.error("该歌手下还有歌曲！删除失败");
        }
        singerService.deleteSinger(id);
        return Result.success("删除成功!");
    }

    /**
     * @description 修改或添加歌手
     * @date 2022/4/5
     * @param singerVO
     * @return com.zzh.common.base.Result
     */
    @ApiOperation(value = "修改或添加歌手")
    @OptLog(optType = SAVE_OR_UPDATE)
    @PostMapping("/admin/saveOrUpdateSinger")
    public Result saveOrUpdateSinger(@RequestBody @Valid SingerVO singerVO) {
        singerService.saveOrUpdateSinger(singerVO);
        return Result.success("保存成功!");
    }
}


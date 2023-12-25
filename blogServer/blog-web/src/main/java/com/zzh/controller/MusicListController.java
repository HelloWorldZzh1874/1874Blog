package com.zzh.controller;


import com.zzh.common.base.BaseController;
import com.zzh.common.base.Result;
import com.zzh.service.MusicListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zzh
 * @since 2022-04-26
 */
@RestController
@RequestMapping("/musicList")
public class MusicListController extends BaseController {

    @Autowired
    private MusicListService musicListService;

    /**
     * @return 返回歌曲列表歌曲信息
     * @description 查询热歌榜歌曲
     * @date 2022/4/26
     */
    @GetMapping("/getList")
    public Result getMusicList() {
        return Result.success(musicListService.getHostListMusic());
    }

    /**
     * @return 返回用户收藏歌曲
     * @description 查询热歌榜歌曲
     * @date 2022/4/26
     */
    @GetMapping("/getCollect")
    public Result getCollect() {
        return Result.success(musicListService.getCollectMusic());
    }
}


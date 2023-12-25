package com.zzh.controller;


import com.zzh.common.base.BaseController;
import com.zzh.common.base.Result;
import com.zzh.entity.Music;
import com.zzh.service.MusicService;
import com.zzh.vo.ConditionVO;
import com.zzh.vo.MusicInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import static com.zzh.common.constant.CommonConst.MUSIC_COVER;

/**
 * <p>
 * 歌曲信息表 前端控制器
 * </p>
 *
 * @author zzh
 * @since 2022-04-03
 */
@RestController
@Api(tags = "音乐模块")
@RequestMapping("/music")
public class MusicController extends BaseController {

    @Autowired
    private MusicService musicService;

    /**
     * @description 根据歌手id查找歌曲
     * @date 2022/4/4
     * @param conditionVO 查询条件
     * @return com.zzh.common.base.Result
     */
    @ApiOperation("根据歌手id查找歌曲")
    @PostMapping("/admin/listBySinger")
    public Result listBySingerId(ConditionVO conditionVO){
        return Result.success(musicService.listMusicBySingerId(conditionVO));
    }

    /**
     * @description 后台查询歌曲列表
     * @date 2022/4/5
     * @param conditionVO 查询条件
     * @return com.zzh.common.base.Result
     */
    @ApiModelProperty("后台查询歌曲列表")
    @PostMapping("/admin/getMusicList")
    public Result getMusicLists(ConditionVO conditionVO){
        return Result.success(musicService.getMusicLists(conditionVO));
    }

    /**
     * @description 更新歌曲图片
     * @date 2022/4/4
     * @param file 图片文件
     * @param id 歌曲id
     * @return com.zzh.common.base.Result
     */
    @ApiOperation("更新图片")
    @PostMapping("/admin/updateImg")
    public Result updateImg(MultipartFile file, Integer id){
        musicService.updateMusicImg(file,id);
        return Result.success();
    }

    /**
     * @description 更新歌曲信息
     * @date 2022/4/4
     * @param musicInfoVO
     * @return com.zzh.common.base.Result
     */
    @ApiOperation("更新歌曲信息")
    @PostMapping("/admin/updateMusicInfo")
    public Result updateMusicInfo(@RequestBody MusicInfoVO musicInfoVO){
        musicService.updateMusicInfo(musicInfoVO);
        return Result.success("更新成功!");
    }

    /**
     * @description 删除歌曲
     * @date 2022/4/5
     * @param id
     * @return com.zzh.common.base.Result
     */
    @ApiOperation("删除歌曲")
    @DeleteMapping("/admin/delete")
    public Result deleteMusic(@RequestParam Integer id){
        musicService.deleteMusic(id);
        return Result.success("删除成功!");
    }

    /**
     * @description 添加歌曲
     * @date 2022/4/5
     * @param request
     * @param mpFile
     * @return com.zzh.common.base.Result
     */
    @ApiOperation("添加歌曲")
    @PostMapping("/admin/add")
    public Result addMusic(HttpServletRequest request, @RequestParam("file") MultipartFile mpFile){
        // 从前端获FormData取值
        // 所著歌手id
        Integer singerId = Integer.valueOf(request.getParameter("singerId").trim());
        // 歌名
        String songName = request.getParameter("name").trim();
        // 简介
        String al = request.getParameter("introduction").trim();
        // 歌词
        String songLyric = request.getParameter("lyric").trim();
        Music music = Music.builder()
                .musicName(songName)
                .musicAl(al)
                .musicImg(MUSIC_COVER)
                .musicLyric(songLyric)
                .singerId(singerId)
                .build();
        musicService.addMusic(music,mpFile);
        return Result.success();
    }

    /**
     * 获取歌曲歌词
     * @param id 歌曲id
     * @return
     */
    @ApiModelProperty("获得歌曲歌词")
    @GetMapping("/lyric")
    public Result getMusicLyric(Integer id){
        return Result.success().setData(musicService.getMusicLyric(id));
    }

    /**
     * 获取歌曲url
     * @param id 歌曲id
     * @return
     */
    @ApiModelProperty("获取歌曲url")
    @GetMapping("/getUrl")
    public Result getMusicUrl(Integer id) {
        return Result.success().setData(musicService.getMusicUrl(id));
    }

    /**
     * @description 添加歌曲到个人收藏
     * @date 2022/4/26
     * @param id 歌曲id
     * @return
     */
    @ApiModelProperty("添加歌曲到个人收藏")
    @PostMapping("/addUserCollect")
    public Result addCollect(Integer id){
        musicService.addCollect(id);
        return Result.success();
    }

    /**
     * @description 移除个人收藏
     * @date 2022/4/26
     * @param id 歌曲id
     * @return
     */
    @ApiModelProperty("移除个人收藏")
    @DeleteMapping("/removeCollect")
    public Result removeCollect(@RequestParam Integer id){
        musicService.removeCollect(id);
        return Result.success();
    }

    /**
     * 通过歌曲名称模糊查询歌曲
     * @param name 歌曲名称关键字
     * @return 返回歌曲列表
     */
    @ApiModelProperty("模糊查询歌曲")
    @GetMapping("/getMusicLike")
    public Result getMusicLike(String name){
        return Result.success(musicService.getMusicLike(name));
    }

}


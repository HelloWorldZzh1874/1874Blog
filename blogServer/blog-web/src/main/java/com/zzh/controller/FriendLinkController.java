package com.zzh.controller;


import com.zzh.aop.OptLog;
import com.zzh.common.base.Result;
import com.zzh.service.FriendLinkService;
import com.zzh.vo.ConditionVO;
import com.zzh.vo.FriendLinkVO;
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
 * @since 2022-03-30
 */
@RestController
@RequestMapping("/friend-link")
@Api(tags = "友链模块")
public class FriendLinkController extends BaseController {

    @Autowired
    FriendLinkService friendLinkService;

    /**
     * @description 查看后台友链
     * @date 2022/3/30
     * @param conditionVO 分页查询条件
     */
    @ApiOperation(value = "查看后台友链")
    @PostMapping("/admin/getLinks")
    public Result getLinks(ConditionVO conditionVO){
        return Result.success(friendLinkService.listBackLinkDTO(conditionVO));
    }

    /**
     * 删除一个或多个友链对象
     * @param linkIdList 友链id list
     * @return
     */
    @ApiOperation(value = "删除友链对象")
    @OptLog(optType = REMOVE)
    @DeleteMapping("/admin/deleteLinks")
    public Result deleteLinks(@RequestBody List<Integer> linkIdList){
        friendLinkService.removeByIds(linkIdList);
        return Result.success();
    }

    /**
     * @description 修改或保存友链
     * @date 2022/3/30
     * @param friendLinkVO 友链数据
     * @return
     */
    @ApiOperation(value = "修改或保存友链")
    @OptLog(optType = SAVE_OR_UPDATE)
    @PostMapping("/admin/save")
    public Result addOrUpdate(@Validated @RequestBody FriendLinkVO friendLinkVO){
        friendLinkService.addOrUpdate(friendLinkVO);
        return Result.success();
    }

    /**
     * @description 查看友链列表
     * @date 2022/4/21
     * @param
     * @return com.zzh.common.base.Result
     */
    @ApiOperation(value = "查看友链列表")
    @GetMapping("/getList")
    public Result listFriendLinks() {
        return Result.success(friendLinkService.getLinkList());
    }
}


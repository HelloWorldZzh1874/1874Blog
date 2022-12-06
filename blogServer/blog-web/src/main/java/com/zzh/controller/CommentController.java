package com.zzh.controller;


import com.zzh.aop.OptLog;
import com.zzh.common.base.BaseController;
import com.zzh.common.base.Result;
import com.zzh.service.CommentService;
import com.zzh.utils.SecurityUtils;
import com.zzh.vo.CommentVO;
import com.zzh.vo.ConditionVO;
import com.zzh.vo.DeleteVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.zzh.aop.OptTypeConst.REMOVE;
import static com.zzh.aop.OptTypeConst.UPDATE;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zzh
 * @since 2022-03-13
 */
@RestController
@RequestMapping("/comment")
@Api(tags = "评论模块")
public class CommentController extends BaseController {

    @Autowired
    CommentService commentService;

    /**
     * @description 查询后台评论列表
     * @date 2022/3/8
     * @param conditionVO 查询条件
     * @return result
     */
    @ApiOperation(value = "后台评论列表")
    @PostMapping("/admin/getBackComments")
    public Result listBackComments(ConditionVO conditionVO) {
        return Result.success(commentService.listBackComments(conditionVO));
    }


    /**
     * @description 物理删除评论
     * @date 2022/3/15
     * @param commentIdList 要删除的id列表
     * @return result
     */
    @OptLog(optType = REMOVE)
    @ApiOperation(value = "物理删除评论")
    @DeleteMapping("/admin/deleteComments")
    public Result deleteComments(@RequestBody List<Integer> commentIdList) {
        commentService.removeByIds(commentIdList);
        return Result.success();
    }

    /**
     * @description 用户删除评论
     * @date 2022/4/30
     * @param id 回复id
     * @return result
     */
    @OptLog(optType = REMOVE)
    @ApiOperation(value = "用户删除评论")
    @DeleteMapping("/deleteUserComment")
    public Result deleteComments(@RequestParam Long id) {
        commentService.removeById(id);
        return Result.success();
    }

    /**
     * @description 文章查询评论，并返回评论与子评论集合
     * @date 2022/4/17
     * @param articleId 文章id
     * @param current
     * @return com.zzh.common.base.Result
     */
    @ApiOperation(value = "查询评论")
    @GetMapping("/{id}/{current}")
    public Result listComments(@PathVariable("id") Integer articleId, @PathVariable("current") int current) {
        return Result.success(commentService.getComment(articleId, current));
    }

    /**
     * @description 查询用户评论
     * @date 2022/4/29
     * @return com.zzh.common.base.Result
     */
    @ApiOperation(value = "查询个人评论")
    @GetMapping("/getUserComment")
    public Result getUserComment() {
        Long id = SecurityUtils.getCurUser().getUser().getId();
        return Result.success(commentService.getUserComment(id));
    }

    /**
     * @description 查询未读消息数量
     * @date 2022/4/30
     * @return com.zzh.common.base.Result
     */
    @ApiOperation(value = "查询未读消息数量")
    @GetMapping("/getNotReadCount")
    public Result getNotReadCount(){
        Long id = SecurityUtils.getCurUser().getUser().getId();
        return Result.success(commentService.getNotReadCount(id));
    }

    /**
     * @description 查询回复
     * @date 2022/4/21
     * @param commentId 评论id
     * @param current 当前页数
     * @return com.zzh.common.base.Result
     */
    @ApiOperation(value = "查询回复")
    @GetMapping("/replies/{commentId}")
    public Result listRepliesByCommentId(@PathVariable("commentId") Integer commentId, Long current) {
        return Result.success(commentService.listRepliesByCommentId(commentId,current));
    }

    /**
     * @description 将回复已读状态更改为已读
     * @date 2022/4/30
     * @param commentId 回复id
     */
    @ApiOperation(value = "改变回复已读状态")
    @PutMapping("/readComment/{commentId}")
    public void changeRead(@PathVariable("commentId") Long commentId){
        commentService.changeRead(commentId);
    }

    /**
     * @description 评论点赞
     * @date 2022/4/21
     * @param commentId 评论id
     * @return com.zzh.common.base.Result
     */
    @ApiOperation(value = "评论点赞")
    @PostMapping("/like")
    public Result saveCommentList(Integer commentId) {
        commentService.saveCommentLike(commentId);
        return Result.success();
    }

    /**
     * @description 添加评论或回复
     * @date 2022/4/21
     * @param commentVO 评论
     * @return com.zzh.common.base.Result
     */
    @ApiOperation(value = "添加评论或回复")
    @PostMapping("/addComment")
    public Result saveComment(@Valid @RequestBody CommentVO commentVO) {
        commentService.saveComment(commentVO);
        return Result.success("发送成功!");
    }
}


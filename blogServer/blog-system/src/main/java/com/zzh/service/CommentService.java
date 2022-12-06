package com.zzh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zzh.dto.*;
import com.zzh.entity.Comment;
import com.zzh.vo.CommentVO;
import com.zzh.vo.ConditionVO;
import com.zzh.vo.DeleteVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zzh
 * @since 2022-03-13
 */
public interface CommentService extends IService<Comment> {
    /**
     * 查找后台评论列表
     *
     * @param conditionVO 查询条件
     * @return 返回分页数据
     * @date 2022/3/8
     */
    PageInfo<CommentBackDTO> listBackComments(ConditionVO conditionVO);


    /**
     * 查询评论列表
     *
     * @param articleId 文章id
     * @param current   页数
     * @return
     */
    PageInfo<CommentDTO> getComment(Integer articleId, int current);

    /**
     * 查看评论下的回复
     *
     * @param commentId 评论id
     * @param current   当前页码
     * @return 回复列表
     */
    List<ReplyDTO> listRepliesByCommentId(Integer commentId, Long current);

    /**
     * 查询用户未读评论
     *
     * @param userId 用户id
     * @return 返回List
     */
    List<CommentUserDTO> getUserComment(Long userId);

    /**
     * 点赞评论
     *
     * @param commentId 评论id
     */
    void saveCommentLike(Integer commentId);

    /**
     * 评论或回复
     *
     * @param commentVO 评论信息
     */
    void saveComment(CommentVO commentVO);

    /**
     * 查询未读消息数量
     * @param id 用户id
     * @return int
     */
    Integer getNotReadCount(Long id);

    /**
     * 将回复已读状态更改为已读
     * @param commentId 回复id
     */
    void changeRead(Long commentId);
}

package com.zzh.mapper;

import com.zzh.dto.*;
import com.zzh.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzh.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zzh
 * @since 2022-03-13
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    /**
     * 擦汗寻后台评论列表
     * @param conditionVO 查询条件
     * @return CommentBackDTO
     */
    List<CommentBackDTO> listCommentBackDTO(@Param("condition") ConditionVO conditionVO);

    /**
     * 查看评论
     *
     * @param articleId 文章id
     * @return 评论集合
     */
    List<CommentDTO> listComments(@Param("articleId") Integer articleId);

    /**
     * 查看评论id集合下的回复
     *
     * @param commentIdList 评论id集合
     * @return 回复集合
     */
    List<ReplyDTO> listReplies(@Param("commentIdList") List<Integer> commentIdList);

    /**
     * 根据评论id查询回复总量
     *
     * @param commentIdList 评论id集合
     * @return 回复数量
     */
    List<ReplyCountDTO> listReplyCountByCommentId(@Param("commentIdList") List<Integer> commentIdList);

    /**
     * 查看当条评论下的回复
     *
     * @param commentId 评论id
     * @param current   当前页码
     * @return 回复集合
     */
    List<ReplyDTO> listRepliesByCommentId(@Param("commentId") Integer commentId, @Param("current") Long current);

    /**
     * 查看评论
     * @param userId 用户id
     * @return list
     */
    List<CommentUserDTO> getUserComment(@Param("userId") Long userId);

    /**
     * 查看未读数量
     * @param id 用户id
     * @return 未读消息数量
     */
    Integer getNotReadCount(@Param("userId") Long id);
}

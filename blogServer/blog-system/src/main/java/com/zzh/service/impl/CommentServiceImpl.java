package com.zzh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzh.aysnc.AsyncManager;
import com.zzh.common.exception.NoDateException;
import com.zzh.common.exception.baseException.CommonWriteException;
import com.zzh.common.utils.HTMLUtil;
import com.zzh.common.utils.RedisUtils;
import com.zzh.dto.*;
import com.zzh.entity.Comment;
import com.zzh.entity.User;
import com.zzh.entity.UserInfo;
import com.zzh.mapper.CommentMapper;
import com.zzh.service.ArticleService;
import com.zzh.service.CommentService;
import com.zzh.service.UserInfoService;
import com.zzh.service.UserService;
import com.zzh.utils.SecurityUtils;
import com.zzh.vo.CommentVO;
import com.zzh.vo.ConditionVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.zzh.common.constant.CommonConst.NOTICE_MAIL;
import static com.zzh.common.constant.RedisConstant.COMMENT_LIKE_COUNT;
import static com.zzh.common.constant.RedisConstant.COMMENT_USER_LIKE;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zzh
 * @since 2022-03-13
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AsyncManager asyncManager;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Override
    public PageInfo<CommentBackDTO> listBackComments(ConditionVO conditionVO) {
        // 转换当页数据
        conditionVO.setCurrent((conditionVO.getCurrent() - 1) * conditionVO.getSize());
        // 查询后台集合
        List<CommentBackDTO> commentBackDTOS = commentMapper.listCommentBackDTO(conditionVO);
        // 获取评论点赞量
        Map<String, Integer> likeCountMap = redisUtils.getMapValue(COMMENT_LIKE_COUNT);
        // 获取点赞数量
        /* redis操作 */
        // 封装点赞量
        commentBackDTOS.forEach(item -> item.setLikeCount(Objects.requireNonNull(likeCountMap).get(item.getId().toString())));
        // 封装成分页数据
        return new PageInfo<>(commentBackDTOS);

    }

    @Override
    public PageInfo<CommentDTO> getComment(Integer articleId, int current) {
        PageHelper.startPage(current, 10);
        // 分页查询评论集合
        List<CommentDTO> commentDTOList = commentMapper.listComments(articleId == -1 ? null : articleId);
        if (commentDTOList.isEmpty()) {
            return new PageInfo<>();
        }
        // 查询redis的评论点赞数据
        Map<String, Integer> likeCountMap = (Map<String, Integer>) redisUtils.getMapValue(COMMENT_LIKE_COUNT);
        // 封装评论点赞量
        // 提取评论id集合
        List<Integer> commentIdList = new ArrayList<>();
        commentDTOList.forEach(item -> {
            commentIdList.add(item.getId());
            item.setLikeCount(Objects.requireNonNull(likeCountMap).get(item.getId().toString()));
        });
        // 根据评论id集合查询回复数据
        List<ReplyDTO> replyDTOList = commentMapper.listReplies(commentIdList);
        // 封装回复点赞量
        replyDTOList.forEach(item -> item.setLikeCount(Objects.requireNonNull(likeCountMap).get(item.getId().toString())));
        // 根据评论id分组回复数据
        Map<Integer, List<ReplyDTO>> replyMap = replyDTOList.stream().collect(Collectors.groupingBy(ReplyDTO::getParentId));
        // 根据评论id查询回复量
        Map<Integer, Integer> replyCountMap = commentMapper.listReplyCountByCommentId(commentIdList)
                .stream().collect(Collectors.toMap(ReplyCountDTO::getCommentId, ReplyCountDTO::getReplyCount));
        // 将分页回复数据和回复量封装进对应的评论
        commentDTOList.forEach(item -> {
            item.setReplyDTOList(replyMap.get(item.getId()));
            item.setReplyCount(replyCountMap.get(item.getId()));
        });
        return new PageInfo<>(commentDTOList);
    }

    @Override
    public List<ReplyDTO> listRepliesByCommentId(Integer commentId, Long current) {
        // 转换页码查询评论下的回复
        List<ReplyDTO> replyDTOList = commentMapper.listRepliesByCommentId(commentId, (current - 1) * 5);
        // 查询redis的评论点赞数据
        Map<String, Integer> likeCountMap = (Map<String, Integer>) redisUtils.getMapValue(COMMENT_LIKE_COUNT);
        // 封装点赞数据
        replyDTOList.forEach(item -> item.setLikeCount(Objects.requireNonNull(likeCountMap).get(item.getId().toString())));
        return replyDTOList;
    }

    @Override
    public List<CommentUserDTO> getUserComment(Long userId) {
        return commentMapper.getUserComment(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCommentLike(Integer commentId) {
        if(commentMapper.selectById(commentId) == null){
            throw new NoDateException("评论数据出错！");
        }
        // 查询当前用户点赞过的评论id集合
        HashSet<Integer> commentLikeSet = (HashSet<Integer>) redisTemplate.boundHashOps(COMMENT_USER_LIKE).get(SecurityUtils.getCurUser().getUser().getId().toString());
        // 第一次点赞则创建
        if (CollectionUtils.isEmpty(commentLikeSet)) {
            commentLikeSet = new HashSet<>();
        }
        // 判断是否点赞
        if (commentLikeSet.contains(commentId)) {
            // 点过赞则删除评论id
            commentLikeSet.remove(commentId);
            // 评论点赞量-1
            redisTemplate.boundHashOps(COMMENT_LIKE_COUNT).increment(commentId.toString(), -1);
        } else {
            // 未点赞则增加评论id
            commentLikeSet.add(commentId);
            // 评论点赞量+1
            redisTemplate.boundHashOps(COMMENT_LIKE_COUNT).increment(commentId.toString(), 1);
        }
        // 保存点赞记录
        redisTemplate.boundHashOps(COMMENT_USER_LIKE).put(SecurityUtils.getCurUser().getUser().getId().toString(), commentLikeSet);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveComment(CommentVO commentVO) {
        // 过滤html标签
        commentVO.setCommentContent(HTMLUtil.deleteCommentTag(commentVO.getCommentContent()));
        Long userId = SecurityUtils.getCurUser().getUser().getId();
        // 构建数据
        // 如果是友链评论则不要articleId
        Comment comment = Comment.builder()
                .userId(userId)
                .replyId(commentVO.getReplyId())
                .articleId(commentVO.getArticleId() == -1 ? null : commentVO.getArticleId())
                .commentContent(commentVO.getCommentContent())
                .parentId(commentVO.getParentId())
                .createTime(new Date())
                .isRead(0)
                .build();
        commentMapper.insert(comment);
        // 如果是友链则不发送邮箱
        if (commentVO.getArticleId() == -1) {
            return;
        }
        /* 邮箱提醒 */
        // 判断是回复用户还是评论作者
        Long id;
        if (Objects.nonNull(commentVO.getReplyId())) {
            // 如果有回复用户id则此评论是回复别人的评论
            id = commentVO.getReplyId();
        } else {
            // 否则视作回复文章id，此时要查出该文章的作者信息
            id = articleService.getById(commentVO.getArticleId()).getUserId();
        }
        // 查询邮箱号
        User replayUser = userService.getById(id);
        UserInfo userInfo = userInfoService.getById(replayUser.getInfoId());
        if (Objects.isNull(userInfo)) {
            throw new CommonWriteException("回复文章或账号不存在,请稍后再试!");
        }
        String email = userInfo.getEmail();
        if (StringUtils.isNotBlank(email)) {
            // 判断页面路径
            // 发送消息,将
            EmailDTO emailDTO = EmailDTO.builder()
                    .email(email)
                    .content(userInfo.getNickname())
                    .subject("评论提醒")
                    .build();
            // 通知用户
            asyncManager.sendMail(emailDTO, NOTICE_MAIL);
        }
    }

    @Override
    public Integer getNotReadCount(Long id) {
        return commentMapper.getNotReadCount(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeRead(Long commentId) {
        // 通过id将回复改为已读
        commentMapper.updateById(
                Comment.builder().id(commentId).isRead(1).build()
        );
    }
}

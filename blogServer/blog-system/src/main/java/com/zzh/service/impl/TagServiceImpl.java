package com.zzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzh.common.constant.HttpStatus;
import com.zzh.common.exception.baseException.CommonWriteException;
import com.zzh.common.utils.BeanCopyUtil;
import com.zzh.dto.TagDTO;
import com.zzh.entity.ConArticleTag;
import com.zzh.entity.Tag;
import com.zzh.mapper.ConArticleTagMapper;
import com.zzh.mapper.TagMapper;
import com.zzh.service.TagService;
import com.zzh.vo.ConditionVO;
import com.zzh.vo.TagVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zzh
 * @since 2022-03-09
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    TagMapper tagMapper;

    @Autowired
    ConArticleTagMapper articleTagMapper;

    @Override
    public PageInfo<Tag> getBackTags(ConditionVO conditionVO) {
        // 分页查询标签列表
        PageHelper.startPage(conditionVO.getCurrent(), conditionVO.getSize());
        // 根据分页信息查询相关标签
        List<Tag> tagList = tagMapper.selectList(new LambdaQueryWrapper<Tag>()
                .select(Tag::getId, Tag::getTagName, Tag::getCreateTime)
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), Tag::getTagName, conditionVO.getKeywords())
                .orderByDesc(Tag::getCreateTime));
        // 创建分页标签数据
        return new PageInfo<>(tagList);

    }

    @Override
    public void deleteTag(List<Integer> deleteTag) {
        //查询标签下是否有文章
        Integer count = articleTagMapper.selectCount(new LambdaQueryWrapper<ConArticleTag>()
                .in(ConArticleTag::getTagId, deleteTag));
        if (count > 0) {
            throw new CommonWriteException("删除失败，该标签下存在文章,请先删除相关文章");
        }
        tagMapper.deleteBatchIds(deleteTag);
    }

    @Override
    public void saveOrUpdateTag(TagVO tagVO) {
        Integer count = tagMapper.selectCount(new LambdaQueryWrapper<Tag>()
                .eq(Tag::getTagName, tagVO.getTagName()));
        if (count > 0) {
            throw new CommonWriteException(HttpStatus.NOT_MODIFIED,"标签名已存在");
        }
        Tag tag = Tag.builder()
                .id(tagVO.getId())
                .tagName(tagVO.getTagName())
                .createTime(Objects.isNull(tagVO.getId()) ? new Date() : null)
                .build();
        this.saveOrUpdate(tag);
    }

    @Override
    public PageInfo<TagDTO> getTagList() {
        List<Tag> list = tagMapper.selectList(new LambdaQueryWrapper<Tag>().select(Tag::getId,Tag::getTagName));
        List<TagDTO> tagDTOList = BeanCopyUtil.copyList(list,TagDTO.class);
        // 标签数量
        Integer count = tagMapper.selectCount(null);
        return new PageInfo<>(tagDTOList,count);
    }
}

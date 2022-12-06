package com.zzh.service;

import com.github.pagehelper.PageInfo;
import com.zzh.dto.TagDTO;
import com.zzh.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzh.vo.ConditionVO;
import com.zzh.vo.TagVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzh
 * @since 2022-03-09
 */
public interface TagService extends IService<Tag> {

    /**
     * 查询后台标签
     * @param conditionVO 查询条件
     * @return 返回分页内容
     */
    PageInfo<Tag> getBackTags(ConditionVO conditionVO);

    /**
     * 根据id列表删除相关标签
     * @param deleteTag 删除标签
     */
    void deleteTag(List<Integer> deleteTag);

    /**
     * 修改或创建一个标签
     * @param tagVO
     */
    void saveOrUpdateTag(TagVO tagVO);

    /**
     * 查看标签列表
     * @return 返回分页数据
     */
    PageInfo<TagDTO> getTagList();
}

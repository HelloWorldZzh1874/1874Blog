package com.zzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzh.common.utils.BeanCopyUtil;
import com.zzh.common.utils.StringUtils;
import com.zzh.dto.FriendLinkDTO;
import com.zzh.dto.LinkDTO;
import com.zzh.entity.FriendLink;
import com.zzh.mapper.FriendLinkMapper;
import com.zzh.service.FriendLinkService;
import com.zzh.vo.ConditionVO;
import com.zzh.vo.FriendLinkVO;
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
 * @since 2022-03-30
 */
@Service
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLink> implements FriendLinkService {

    @Autowired
    FriendLinkMapper friendLinkMapper;

    @Override
    public PageInfo<FriendLinkDTO> listBackLinkDTO(ConditionVO conditionVO) {
        PageHelper.startPage(conditionVO.getCurrent(), conditionVO.getSize());
        List<FriendLink> links = friendLinkMapper.selectList(new LambdaQueryWrapper<FriendLink>()
                .select(FriendLink::getId, FriendLink::getLinkName, FriendLink::getLinkAvatar, FriendLink::getLinkAddress, FriendLink::getLinkIntro, FriendLink::getCreateTime)
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), FriendLink::getLinkName, conditionVO.getKeywords()));
        List<FriendLinkDTO> friendLinkBackDTOList = BeanCopyUtil.copyList(links, FriendLinkDTO.class);
        return new PageInfo<>(friendLinkBackDTOList);
    }

    @Override
    public void addOrUpdate(FriendLinkVO friendLinkVO) {
        // 生成数据库对象
        FriendLink friendLink = FriendLink.builder()
                .id(friendLinkVO.getId())
                .linkName(friendLinkVO.getLinkName())
                .linkAvatar(friendLinkVO.getLinkAvatar())
                .linkAddress(friendLinkVO.getLinkAddress())
                .linkIntro(friendLinkVO.getLinkIntro())
                .createTime(Objects.isNull(friendLinkVO.getId()) ? new Date() : null)
                .build();
        // 数据库操作
        this.saveOrUpdate(friendLink);
    }

    @Override
    public List<LinkDTO> getLinkList() {
        // 查询友链列表
        List<FriendLink> friendLinkList = friendLinkMapper.selectList(new LambdaQueryWrapper<FriendLink>()
                .select(FriendLink::getId, FriendLink::getLinkName, FriendLink::getLinkAvatar, FriendLink::getLinkAddress, FriendLink::getLinkIntro));
        return BeanCopyUtil.copyList(friendLinkList, LinkDTO.class);
    }
}

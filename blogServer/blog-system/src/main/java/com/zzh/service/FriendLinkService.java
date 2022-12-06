package com.zzh.service;

import com.github.pagehelper.PageInfo;
import com.zzh.dto.FriendLinkDTO;
import com.zzh.dto.LinkDTO;
import com.zzh.entity.FriendLink;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzh.vo.ConditionVO;
import com.zzh.vo.FriendLinkVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzh
 * @since 2022-03-30
 */
public interface FriendLinkService extends IService<FriendLink> {
    /**
     * 查询后台友链
     * @param conditionVO 分页条件
     * @return 返回分页数据
     */
    PageInfo<FriendLinkDTO> listBackLinkDTO(ConditionVO conditionVO);


    /**
     * 修改或保存友链
     * @param friendLinkVO 友链数据
     */
    void addOrUpdate(FriendLinkVO friendLinkVO);

    /**
     * 查询前端友链列表
     * @return
     */
    List<LinkDTO> getLinkList();

}

package com.zzh.service;

import com.zzh.dto.BlogBackInfoDTO;
import com.zzh.dto.BlogHomeInfoDTO;

/**
 * @description 博客信息业务
 * @author zzh
 * @date 2022/3/31
 */
public interface BlogService {
    /**
     * 从Redis獲取關於我信息
     * @return 返回关于我string
     */
    String getAbout();

    /**
     * 获取公告
     * @return
     */
    String getNotice();

    /**
     * 设置公告
     * @param notice
     */
    void updateNotice(String notice);

    /**
     * 修改关于我信息
     * @param aboutContent 关于我的内容
     */
    void updateAbout(String aboutContent);

    /**
     * 获取后台博客信息
     * @return BlogBackInfoDTO
     */
    BlogBackInfoDTO getBlogBackInfo();

    /**
     * 查看首页博客信息
     * @return BlogHomeInfoDTO
     */
    BlogHomeInfoDTO getHomeBlog();

}

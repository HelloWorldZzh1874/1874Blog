package com.zzh.common.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @description 路径相关枚举
 * @date 2022/3/8
 * @author zzh
 */

@Getter
@AllArgsConstructor
public enum FilePathEnum {
    /**
     * 头像路径
     */
    AVATAR("AvatarImg/", "头像路径"),
    /**
     * 文章图片路径
     */
    ARTICLE("Articles/", "文章图片路径"),
    /**
     * 文章图片路径
     */
    ARTICLE_COVER("Articles/COVER/", "文章图片路径"),
    /**
     * 音频路径
     */
    VOICE("Voice/", "音频路径"),

    /**
     * oss服务器路径
     */
    OSS_URL("https://1874centos-1304996288.cos.ap-chongqing.myqcloud.com/","oss路径"),

    /**
     * 默认头像路径
     */
    DefaultAvatar("https://1874centos-1304996288.cos.ap-chongqing.myqcloud.com/AvatarImg/catAvatar.webp","默认头像"),


    /**
     * 默认封面路径
     */
    DefaultCover("https://1874centos-1304996288.cos.ap-chongqing.myqcloud.com/Articles/DefaultCover.webp","文章默认图片");

    /**
     * 路径
     */
    private final String path;

    /**
     * 描述
     */
    private final String desc;
}

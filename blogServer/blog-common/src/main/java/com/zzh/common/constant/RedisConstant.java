package com.zzh.common.constant;
/**
 * @author zzh
 * @description TODO
 * @date 2022/1/26 10:17
 */
public class RedisConstant {

    /**
     * redis存储token前缀
     */
    public static final String TOKEN_PRE = "login:";

    /**
     * 登录错误前缀
     */
    public static final String LOGIN_FAILURE_PRE = "loginFailure:";

    /**
     * token存储redis中的key的一部分
     */
    public static final String LOGIN_TOKEN = "login_token";

    /**
     * 文章浏览量
     */
    public static final String ARTICLE_VIEWS_COUNT = "article_views_count";

    /**
     * 文章浏览
     */
    public static final String ARTICLE_VIEW = "article_view";

    /**
     * 文章点赞量
     */
    public static final String ARTICLE_LIKE_COUNT = "article_like_count";

    /**
     * 评论点赞量
     */
    public static final String COMMENT_LIKE_COUNT = "comment_like_count";

    /**
     * 关于我信息
     */
    public static final String ABOUT_ME = "about_me";

    /**
     * 博客浏览量
     */
    public static final String BLOG_VIEWS_COUNT = "blog_views_count";

    /**
     * 公告
     */
    public static final String NOTICE = "notice";

    /**
     * 用户点赞文章
     */
    public static final String ARTICLE_USER_LIKE = "article_user_like";

    /**
     * 用户点赞评论
     */
    public static final String COMMENT_USER_LIKE = "comment_user_like";

    /**
     * 验证码
     */
    public static final String CODE_KEY = "code_";


    /**
     * 验证码过期时间
     */
    public static final long CODE_EXPIRE_TIME = 15 * 60 * 1000;

    /**
     * 每日访问量ip集合
     */
    public static final String IP_SET = "ip_set";
}

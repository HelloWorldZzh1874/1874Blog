package com.zzh.common.constant;

import java.util.*;

/**
 * @author zzh
 * @description 相关常量
 * @date 2022/1/28 21:03
 */
public class CommonConst {
    /**
     * 账户锁定次数
     */
    public static final Integer ACCOUNT_LOCK = 5;


    /**
     * 默认用户头像
     */
    public static final String DEFAULT_AVATAR = "https://1874centos-1304996288.cos.ap-chongqing.myqcloud.com/AvatarImg/catAvatar.webp";


    /**
     * 前端记住我参数名
     */
    public static final String REMEMBER_ME = "rememberMe";

    /**
     * 网站修改密码连接
     */
    public static final String PASSWD_URL = "http://www.zzh1874.com/";

    /**
     * 博主id
     */
    public static final Long BLOGGER_ID = 12L;

    /**
     * false的整数值
     */
    public static final Integer FALSE = 0;

    /**
     * true的整数值
     */
    public static final Integer TRUE = 1;

    /**
     * 修改菜单表示
     */
    public static final Integer EDIT_MENU = 1;

    /**
     * 修改资源表示
     */
    public static final Integer EDIT_RES = 2;

    /**
     * 歌手默认图片
     */
    public static final String SINGER_COVER = "/img/singerImg/singer_cover.jpg";

    /**
     * 歌手头像路径
     */
    public static final String SINGER_IMG_PATH = System.getProperty("user.dir") + System.getProperty("file.separator")+
            "img"+System.getProperty("file.separator")+"singerImg";

    /**
     * 歌曲默认图片
     */
    public static final String MUSIC_COVER = "/img/musicImg/music_cover.jpg";

    /**
     * 音乐图片路径
     */
    public static final String MUSIC_IMG_PATH = System.getProperty("user.dir") + System.getProperty("file.separator")+
            "img"+ System.getProperty("file.separator")+"musicImg";


    /**
     * 音乐文件路径
     */
    public static final String MUSIC_FILE_PATH = System.getProperty("user.dir") + System.getProperty("file.separator")+
            "musics";

    /**
     * 音乐文件类型key
     */
    public static final String MUSIC_FILE_TYPE = "musicFileType";

    /**
     * 音乐文件类型key
     */
    public static final String PIC_FILE_TYPE = "picFileType";

    /**
     * 上传文件的类型限制
     */
    public static final Map<String, List<String>> FILE_TYPE;

    /**
     * 匿名
     */
    public static final String ANONYMOUS_USER = "anonymousUser";


    /**
     * 邮箱类型--错误的密码
     */
    public static final Integer WRONG_PWD = 0;

    /**
     * 邮箱类型--注册验证码
     */
    public static final Integer REGISTER_CODE_EMAIL = 1;

    /**
     * 邮箱类型--忘记密码验证码
     */
    public static final Integer FORGET_PWD_CODE_EMAIL = 2;

    /**
     * 邮箱类型--回复提醒
     */
    public static final Integer NOTICE_MAIL = 3;

    /**
     * 一般邮箱模板
     */
    public static final String COMMON_EMAIL_TEMP = "commonTemplate";

    /**
     * 验证码邮箱模板
     */
    public static final String CODE_EMAIL_TEMP = "codeTemplate";

    /**
     * 验证码邮箱模板
     */
    public static final String NOTICE_EMAIL_TEMP = "noticeTemplate";

    /**
     * 歌单类型--歌曲列表
     */
    public static final Integer HOT_MUSIC_TYPE = 1;

    /**
     * 歌单类型--用户收藏
     */
    public static final Integer COLLECT_MUSIC = -1;

    // 对文件类型的初始化
    static {
        FILE_TYPE = new HashMap<>();
        List<String> musicType = new ArrayList<>(Arrays.asList(
                "flac",
                "mp3")
        );
        FILE_TYPE.put(MUSIC_FILE_TYPE,musicType);
        List<String> picType = new ArrayList<>(Arrays.asList(
                "jpg",
                "jpeg",
                "png",
                "bmp")
        );
        FILE_TYPE.put(PIC_FILE_TYPE,picType);
    }
}

package com.zzh.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author zzh
 * @Date 2022/3/25 8:46
 * @Version 0.1
 * @Description Web配置 文件路径配置
 **/

@Configuration
public class FileConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 对用户头像定位
        registry.addResourceHandler("/img/avatarImg/**").addResourceLocations(
                "file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "img"
                        + System.getProperty("file.separator") + "avatarImg" + System.getProperty("file.separator")
        );

        // 对歌曲图片定位
        registry.addResourceHandler("/img/musicImg/**").addResourceLocations(
                "file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "img"
                        + System.getProperty("file.separator") + "musicImg" + System.getProperty("file.separator")
        );

        // 对歌手图片定位
        registry.addResourceHandler("/img/singerImg/**").addResourceLocations(
                "file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "img"
                        + System.getProperty("file.separator") + "singerImg" + System.getProperty("file.separator")
        );

        // 对歌曲资源定位
        registry.addResourceHandler("/musics/**").addResourceLocations(
                "file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "musics" + System.getProperty("file.separator")
        );
    }


}

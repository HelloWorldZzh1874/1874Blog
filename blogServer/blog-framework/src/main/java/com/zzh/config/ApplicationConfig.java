package com.zzh.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author zzh
 * @Date 2022/1/17 19:52
 * @Version 0.1
 * @Description Swagger相关配置参数
 **/

@Component
@ConfigurationProperties(prefix = "blog")
public class ApplicationConfig {
    /** 项目名 */
    private String name;

    /** 项目版本 */
    private String version;

    /** 项目时间 */
    private String copyrightYear;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCopyrightYear() {
        return copyrightYear;
    }

    public void setCopyrightYear(String copyrightYear) {
        this.copyrightYear = copyrightYear;
    }
}

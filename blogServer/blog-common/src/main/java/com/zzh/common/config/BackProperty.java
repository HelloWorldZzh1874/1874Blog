package com.zzh.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zzh
 * @description 备份配置文件
 */
@Setter
@Getter
@Component
public class BackProperty {

    @Value("${back.databaseUsername}")
    private String databaseUsername;

    @Value("${back.databasePwd}")
    private String databasePwd;

    @Value("${back.database}")
    private List<String> databases;

    @Value("${back.dir}")
    private String backupDir;

    @Value("${back.host}")
    private String host;

    @Value("${back.hostUsername}")
    private String hostUsername;

    @Value("${back.hostPwd}")
    private String hostPwd;
}

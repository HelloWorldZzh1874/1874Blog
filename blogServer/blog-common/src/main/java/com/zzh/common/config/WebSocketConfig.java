package com.zzh.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Author zzh
 * @Date 2022/4/25 15:15
 * @Version 0.1
 * @Description WebSocket配置--开启webSocket支持
 **/

@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}


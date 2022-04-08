package com.zhihao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Description:
 * @Author: 王憨憨
 * @Date: 2022/2/22 17:34
 * @version:1.0
 */
@Configuration
public class WebsocketConfiguration {
    @Bean
    public ServerEndpointExporter  serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}

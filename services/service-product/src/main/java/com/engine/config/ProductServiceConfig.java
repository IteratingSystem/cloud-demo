package com.engine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Auther WenLong
 * @Date 2025/6/5 9:53
 * @Description 商品配置类
 **/
@Configuration
public class ProductServiceConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

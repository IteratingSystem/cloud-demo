package org.example.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Value("${resttemplate.connect-timeout:3000}")
    private int connectTimeout;

    @Value("${resttemplate.read-timeout:3000}")
    private int readTimeout;

    @Bean
    @LoadBalanced
    // @SentinelRestTemplate(...)  // 先注释掉，后续再研究
    public RestTemplate internalRestTemplate() {
        return createRestTemplate();
    }

    @Bean
    public RestTemplate externalRestTemplate() {
        return createRestTemplate();
    }

    private RestTemplate createRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(connectTimeout);
        factory.setReadTimeout(readTimeout);
        return new RestTemplate(factory);
    }
}
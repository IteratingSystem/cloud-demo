package com.engine.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Auther WenLong
 * @Date 2025/6/5 14:31
 * @Description Nacos配置文件映射
 **/

@Data
@Component
@ConfigurationProperties(prefix = "order")
public class OrderProperties {
    private String timeout;
    private String autoConfirm;
    private String url;
}

package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.PropertySource;

/**
 * 基本controller的demo包含了各种请求示例
 */
@SpringBootApplication
@EnableDiscoveryClient
@PropertySource("classpath:application.yml")
public class ControllerDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ControllerDemoApplication.class, args);
    }
}

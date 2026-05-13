package org.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * openFeign使用demo
 */

@SpringBootApplication
@EnableDiscoveryClient
//此demo关键注解
@EnableFeignClients
public class OpenFeignDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpenFeignDemoApplication.class, args);
    }
}

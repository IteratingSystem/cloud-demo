package com.engine;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @Auther WenLong
 * @Date 2025/6/3 17:34
 * @Description 订单服务
 **/
//开启feign客户端
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class OrderMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderMainApplication.class, args);
    }


    //1. 项目启动就监听Nacos配置文件的变化
    //2. 拿到变化的值
    //3. 模拟右键通知
    @Bean   //Bean后可直接使用BeanFactory内部已有的工具作为入参
    ApplicationRunner applicationRunner(NacosConfigManager nacosConfigManager) {
//        return new ApplicationRunner() {
//            @Override
//            public void run(ApplicationArguments args) throws Exception {
//            }
//        };
        //简写
        return args -> {
            System.out.println("Application Started,项目启动");
            ConfigService configService = nacosConfigManager.getConfigService();    //获取服务
            configService.addListener("service-order.properties", "DEFAULT_GROUP", new Listener() {
                //线程池
                @Override
                public Executor getExecutor() {
                    //需要new一个固定大小的线程池
                    return Executors.newFixedThreadPool(4);
                }
                //监听接收变化配置
                @Override
                public void receiveConfigInfo(String configInfo) {
                    System.out.println("变化配置信息:");
                    System.out.println(configInfo);
                    System.out.println("发送通知...");
                }
            });
        };
    }
}

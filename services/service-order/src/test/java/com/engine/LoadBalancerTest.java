package com.engine;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

/**
 * @Auther WenLong
 * @Date 2025/6/5 10:31
 * @Description 负载均衡测试
 **/
@SpringBootTest
public class LoadBalancerTest {
    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Test
    void test(){
        ServiceInstance choose = loadBalancerClient.choose("service-product");
        System.out.println("choose = "+choose.getUri());
        choose = loadBalancerClient.choose("service-product");
        System.out.println("choose = "+choose.getUri());
        choose = loadBalancerClient.choose("service-product");
        System.out.println("choose = "+choose.getUri());
        choose = loadBalancerClient.choose("service-product");
        System.out.println("choose = "+choose.getUri());
        choose = loadBalancerClient.choose("service-product");
        System.out.println("choose = "+choose.getUri());
        choose = loadBalancerClient.choose("service-product");
        System.out.println("choose = "+choose.getUri());
        choose = loadBalancerClient.choose("service-product");
        System.out.println("choose = "+choose.getUri());
        choose = loadBalancerClient.choose("service-product");
        System.out.println("choose = "+choose.getUri());
    }
}

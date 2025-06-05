package com.engine.service.impl;


import com.engine.order.bean.Order;
import com.engine.product.bean.Product;
import com.engine.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther WenLong
 * @Date 2025/6/4 15:12
 * @Description 订单业务实现类
 **/
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    DiscoveryClient discoveryClient;
    @Autowired
    LoadBalancerClient loadBalancerClient;
    @Autowired
    RestTemplate restTemplate;

    @Override
    public Order createOrder(Long productId, Long userId) {
        Product product = getProductFromRemoteWithLoadBalancerAnnotation(productId);

        Order order = new Order();
        order.setId(1L);
        BigDecimal totalAmount = product.getPrice().multiply(BigDecimal.valueOf(product.getNum()));
        order.setTotalAmount(totalAmount);
        order.setUserId(userId);
        order.setNickName("张三");
        order.setAddress("官方旗舰店");
        order.setProductList(Arrays.asList(product));
        return order;
    }

    //远程调用商品服务(无负载均衡)
    private Product getProductFromRemote(Long productId) {
        //1.获取商品服务所在的url
        List<ServiceInstance> instances = discoveryClient.getInstances("service-product");
        ServiceInstance serviceInstance = instances.get(0);
        String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/product/" + productId;
        log.info("远程请求:{}", url);
        //2.发送远程请求
        Product product = restTemplate.getForObject(url, Product.class);
        return product;
    }
    //远程调用商品服务(负载均衡)
    private Product getProductFromRemoteWithLoadBalancer(Long productId) {
        //1.获取商品服务所在的url
        ServiceInstance choose = loadBalancerClient.choose("service-product");
        //String url = "http://" + choose.getHost() + ":" + choose.getPort() + "/product/" + productId;
        String url = choose.getUri() + "/product/" + productId;
        log.info("远程请求:{}", url);
        //2.发送远程请求
        Product product = restTemplate.getForObject(url, Product.class);
        return product;
    }
    //远程调用商品服务(注解负载均衡)
    private Product getProductFromRemoteWithLoadBalancerAnnotation(Long productId) {
        //1.获取商品服务所在的url
        String url = "http://service-product/product/" + productId;
        //2.发送远程请求
        Product product = restTemplate.getForObject(url, Product.class);
        return product;
    }
}

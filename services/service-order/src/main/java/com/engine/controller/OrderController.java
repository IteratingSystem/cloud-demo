package com.engine.controller;


import com.engine.order.bean.Order;
import com.engine.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther WenLong
 * @Date 2025/6/4 15:10
 * @Description 订单接口
 **/

@RefreshScope
@RestController
public class OrderController {
    @Autowired
    OrderService orderService;


    @Value("${order.timeout}")
    String orderTimeout;
    @Value("${order.auto-confirm}")
    String orderAutoConfirm;

    @GetMapping("/config")
    public String config(){
        return "orderTimeout:" + orderTimeout+";"+"orderAutoConfirm:" + orderAutoConfirm;
    }

    //创建订单
    @GetMapping("/create")
    public Order createOrder(@RequestParam("productId") Long productId,
                             @RequestParam("userId") Long userId){
        Order order = orderService.createOrder(productId,userId);
        return order;
    }
}

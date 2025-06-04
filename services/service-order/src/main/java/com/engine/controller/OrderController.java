package com.engine.controller;

import com.engine.bean.Order;
import com.engine.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther WenLong
 * @Date 2025/6/4 15:10
 * @Description 订单接口
 **/

@RestController
public class OrderController {
    @Autowired
    OrderService orderService;
    //创建订单
    @GetMapping("/create")
    public Order createOrder(@RequestParam("productId") Long productId,
                             @RequestParam("userId") Long userId){
        Order order = orderService.createOrder(productId,userId);
        return order;
    }
}

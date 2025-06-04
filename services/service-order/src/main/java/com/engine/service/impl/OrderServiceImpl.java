package com.engine.service.impl;

import com.engine.bean.Order;
import com.engine.service.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @Auther WenLong
 * @Date 2025/6/4 15:12
 * @Description 订单业务实现类
 **/
@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public Order createOrder(Long productId, Long userId) {
        Order order = new Order();
        order.setId(1L);
        order.setTotalAmount(new BigDecimal(0));
        order.setUserId(userId);
        order.setNickName("张三");
        order.setAddress("官方旗舰店");
        order.setProductList(null);
        return order;
    }
}

package com.engine.service;

import com.engine.bean.Order;

public interface OrderService {
    Order createOrder(Long productId, Long userId);
}

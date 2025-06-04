package com.engine.service;

import com.engine.bean.Product;

/**
 * @Auther WenLong
 * @Date 2025/6/4 15:00
 * @Description 商品业务
 **/
public interface ProductService {
    Product getProductById(Long productId);
}

package com.engine.service.impl;


import com.engine.product.bean.Product;
import com.engine.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @Auther WenLong
 * @Date 2025/6/4 15:02
 * @Description 商品业务实现类
 **/

@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public Product getProductById(Long productId) {
        Product product = new Product();
        product.setId(productId);
        product.setPrice(new BigDecimal(99));
        product.setProductName("苹果-"+productId);
        product.setNum(2);
        return product;
    }
}

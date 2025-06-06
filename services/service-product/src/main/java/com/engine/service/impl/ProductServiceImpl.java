package com.engine.service.impl;


import ch.qos.logback.core.util.TimeUtil;
import com.engine.product.bean.Product;
import com.engine.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

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

//        //模拟超时,休眠100秒后返回
//        try {
//            TimeUnit.SECONDS.sleep(100);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        return product;
    }
}

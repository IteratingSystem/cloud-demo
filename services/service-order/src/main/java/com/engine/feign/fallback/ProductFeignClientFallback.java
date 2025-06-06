package com.engine.feign.fallback;

import com.engine.feign.ProductFeignClient;
import com.engine.product.bean.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @Auther WenLong
 * @Date 2025/6/6 17:35
 * @Description 兜底回调
 **/
@Component
public class ProductFeignClientFallback implements ProductFeignClient {
    @Override
    public Product getProduct(Long productId) {
        Product product = new Product();
        product.setId(666L);
        product.setPrice(new BigDecimal(0));
        product.setProductName("兜底数据");
        product.setNum(1);
        return product;
    }
}

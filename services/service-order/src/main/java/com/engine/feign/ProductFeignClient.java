package com.engine.feign;

import com.engine.feign.fallback.ProductFeignClientFallback;
import com.engine.product.bean.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-product",fallback = ProductFeignClientFallback.class )
public interface ProductFeignClient {
    @GetMapping("/product/{id}")
    Product getProduct(@PathVariable("id") Long productId);
}

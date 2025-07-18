package com.engine.comtroller;


import com.engine.product.bean.Product;
import com.engine.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther WenLong
 * @Date 2025/6/4 14:59
 * @Description 商品接口
 **/
@RestController
public class ProductController {
    @Autowired
    ProductService productService;
    //查询商品
    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable("id") Long productId){
        Product product = productService.getProductById(productId);
        return product;
    }
}

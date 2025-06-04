package com.engine.bean;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Auther WenLong
 * @Date 2025/6/4 14:55
 * @Description 商品实体
 **/
@Data
public class Product {
    private Long id;
    private BigDecimal price;
    private String productName;
    private int num;
}

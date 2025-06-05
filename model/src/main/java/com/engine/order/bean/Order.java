package com.engine.order.bean;

import com.engine.product.bean.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Auther WenLong
 * @Date 2025/6/4 15:13
 * @Description 订单实体
 **/
@Data
public class Order {
    private Long id;
    private BigDecimal totalAmount;
    private Long userId;
    private String nickName;
    private String address;
    private List<Product> productList;
}

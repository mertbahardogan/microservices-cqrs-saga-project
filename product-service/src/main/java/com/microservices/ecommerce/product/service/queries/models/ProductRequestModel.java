package com.microservices.ecommerce.product.service.queries.models;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequestModel {

    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;
}
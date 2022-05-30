package com.microservices.ecommerce.product.service.core.events;

import lombok.Data;

import java.math.BigDecimal;

//Same class as CreateProductCommand in terms of fields.

@Data
public class ProductCreatedEvent {

    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;
}

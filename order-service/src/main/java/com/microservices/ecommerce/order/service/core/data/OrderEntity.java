package com.microservices.ecommerce.order.service.core.data;

import com.microservices.ecommerce.order.service.core.enums.OrderStatus;

import javax.persistence.*;

@Entity
@Table(name="orders")
public class OrderEntity {

    @Id
    @Column(unique = true)
    public String orderId;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private String productId;
    private String userId;
    private int quantity;
    private String addressId;
}

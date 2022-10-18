package com.microservices.ecommerce.order.service.core.data;

import com.microservices.ecommerce.order.service.core.enums.OrderStatus;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="orders")
public class OrderEntity implements Serializable {

    private static final long serialVersionUID = 5313493413859894403L;

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

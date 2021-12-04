package com.microservices.ecommerce.product.service.core.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

//SEARCH: Entity neye karşılık geliyordu?
@Entity
@Table(name = "products")
@Data
public class ProductEntity implements Serializable {

    public static final Long serialVersionUID = -227781238123213L;

    @Id
    @Column(unique = true)
    private String productId;
    @Column(unique = true)
    private String title;
    private BigDecimal price;
    private Integer quantity;
}

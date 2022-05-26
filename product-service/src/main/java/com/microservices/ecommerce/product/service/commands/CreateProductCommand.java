package com.microservices.ecommerce.product.service.commands;

import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

/*
@Builder annotation provides Builder Design Pattern benefits.

@TargetAggregateIdentifier field or method level annotation.
This annotation required for aggregate id in command class
*/

@Builder
@Getter
public class CreateProductCommand {

    @TargetAggregateIdentifier
    private final String productId; //debuglayalım axon içerisinde neye dönüşüyor?
    private final String title;
    private final BigDecimal price;
    private final Integer quantity;
}

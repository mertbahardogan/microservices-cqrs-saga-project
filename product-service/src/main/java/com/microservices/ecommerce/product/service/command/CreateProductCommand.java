package com.microservices.ecommerce.product.service.command;

import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

/*
@Builder annotation provides Builder Design Pattern benefits.

@TargetAggregateIdentifier field or method level annotation.
This annotation is required to indicate the Aggregate ID in the command class.
*/

@Builder
@Getter
public class CreateProductCommand {

    @TargetAggregateIdentifier
    private final String productId; // Aggregate ID
    private final String title;
    private final BigDecimal price;
    private final Integer quantity;
}

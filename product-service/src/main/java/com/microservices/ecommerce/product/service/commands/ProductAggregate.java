package com.microservices.ecommerce.product.service.commands;

import com.microservices.ecommerce.product.service.core.events.ProductCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

/*
Aggregate is entity set. Entity (Product) State, Command Handlers (Handle CreateProductCommand), Business Logic (Validation, desicion making),
Event Handlers (Event Sourcing Handler Methods) are included in the Aggregate Class (ProductAggregate).
Command Bus -> Aggregate -> Event Bus
 */

@Aggregate
public class ProductAggregate {

    @AggregateIdentifier  // should be String, UUD or Integer (not primitive) etc.
    private String productId; // this field must match create Product Command productId otherwise aggregate not found error will be returned.
    private String title;
    private BigDecimal price;
    private Integer quantity;

    // First Const: Default, NoArgsConst
    public ProductAggregate(){


    }

    // Second Const
    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand){
        // Validate CreateProductCommand

        if(createProductCommand.getPrice().compareTo(BigDecimal.ZERO)<=0){
            throw new IllegalArgumentException("Price cannot be less or equal than zero");
        }
        if(createProductCommand.getTitle()==null||createProductCommand.getTitle().isBlank()){
            throw new IllegalArgumentException("Title cannot be empty");
        }

        ProductCreatedEvent productCreatedEvent=new ProductCreatedEvent();
        // The following code snippet allows copying the createProductCommand object to the productCreatedEvent object.
        BeanUtils.copyProperties(createProductCommand,productCreatedEvent);
        // The Apply method is used to publish an event over an aggregate.
        // While executing a command, it notifies the rest of the application that a new event has been created.
        AggregateLifecycle.apply(productCreatedEvent);
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent productCreatedEvent){
        this.productId=productCreatedEvent.getProductId();
        this.title=productCreatedEvent.getTitle();
        this.price=productCreatedEvent.getPrice();
        this.quantity=productCreatedEvent.getQuantity();
    }
}

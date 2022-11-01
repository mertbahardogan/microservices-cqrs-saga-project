package com.microservices.ecommerce.order.service.saga;

import com.microservices.ecommerce.commands.ReserveProductCommand;
import com.microservices.ecommerce.events.ProductReservedEvent;
import com.microservices.ecommerce.order.service.core.events.OrderCreatedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class OrderSaga {
    //Saga, StartSaga, EndSaga need to have that annotations.

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderSaga.class);
    private transient CommandGateway commandGateway;
    @Autowired
    public OrderSaga(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    //Catch Up when OrderCreatedEvent published
    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent orderCreatedEvent) {
        ReserveProductCommand reserveProductCommand = ReserveProductCommand.builder()
                .orderId(orderCreatedEvent.getOrderId())
                .productId(orderCreatedEvent.getProductId())
                .quantity(orderCreatedEvent.getQuantity())
                .userId(orderCreatedEvent.getUserId())
                .build();

        LOGGER.info("OrderCreatedEvent handled for orderId: " + reserveProductCommand.getOrderId()
                + " and productId: " + reserveProductCommand.getProductId());

        commandGateway.send(reserveProductCommand, (commandMessage, commandResultMessage) -> {
            if (commandResultMessage.isExceptional()) {
                // Start a compensating transaction
            }
        });
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservedEvent productReservedEvent) {
        //Process user payment
        LOGGER.info("ProductReservedEvent is called for orderId: " + productReservedEvent.getOrderId()
                + " and productId: " + productReservedEvent.getProductId());
    }
}

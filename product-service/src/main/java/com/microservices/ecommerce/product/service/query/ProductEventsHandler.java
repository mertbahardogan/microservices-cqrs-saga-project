package com.microservices.ecommerce.product.service.query;

import com.microservices.ecommerce.events.ProductReservedEvent;
import com.microservices.ecommerce.product.service.core.data.ProductEntity;
import com.microservices.ecommerce.product.service.core.dataAccess.ProductsRepository;
import com.microservices.ecommerce.product.service.core.events.ProductCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/*
Projection Class
The method (event handler) provides, that every time an event comes from the commandGateway, this method catches the event and we can do whatever we want.
*/
@Component
@ProcessingGroup("product-group")
public class ProductEventsHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductEventsHandler.class);
    private final ProductsRepository productsRepository;

    public ProductEventsHandler(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    // The both of methods, at the following, depending to ProductsServiceEventsHander class. The configure at Application class.
    @ExceptionHandler(resultType = Exception.class)
    public void handle(Exception exception) throws Exception {
        throw exception;
    }

    @ExceptionHandler(resultType = IllegalArgumentException.class)
    public void handle(IllegalArgumentException exception) {
        // Log error message
    }

    // Consume Command Event
    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent) throws Exception {
        // Save Event through Entity to DB

        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(productCreatedEvent, productEntity);

        try {
            productsRepository.save(productEntity);
        } catch (IllegalArgumentException exception) {
            exception.printStackTrace();   //TODO: What is that?
        }

//        if(true) throw new Exception("An error took place in the CreateProductCommand @CommandHandler method"); // Try for see Error Process.
    }

    @EventHandler
    public void on(ProductReservedEvent productReservedEvent) throws Exception {
        ProductEntity productEntity = productsRepository.findByProductId(productReservedEvent.getProductId());
        productEntity.setQuantity(productEntity.getQuantity() - productReservedEvent.getQuantity());
        productsRepository.save(productEntity);

        LOGGER.info("ProductReservedEvent is called for productId: " + productReservedEvent.getProductId()
                + " and orderId: " + productReservedEvent.getOrderId());
    }
}

package com.microservices.ecommerce.product.service.query;

import com.microservices.ecommerce.product.service.core.data.ProductEntity;
import com.microservices.ecommerce.product.service.core.dataAccess.ProductDao;
import com.microservices.ecommerce.product.service.core.events.ProductCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/*
Projection Class
The method (event handler) provides, that every time an event comes from the commandGateway, this method catches the event and we can do whatever we want.
*/
@Component
@ProcessingGroup("product-group")
public class ProductEventsHandler {

    private final ProductDao productDao;

    public ProductEventsHandler(ProductDao productDao) {
        this.productDao = productDao;
    }

    // The both of methods, at the following, depending to ProductsServiceEventsHander class. The configure at Application class.
    @ExceptionHandler(resultType = Exception.class)
    public void handle(Exception exception) throws Exception{
        throw exception;
    }

    @ExceptionHandler(resultType = IllegalArgumentException.class)
    public void handle(IllegalArgumentException exception) {
        // Log error message
    }

    // Consume Command Event
    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent) throws Exception{
        // Save Event through Entity to DB

        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(productCreatedEvent, productEntity);

        try {
            productDao.save(productEntity);
        } catch (IllegalArgumentException exception) {
            exception.printStackTrace();
        }

        if(true) throw new Exception("An error took place in the CreateProductCommand @CommandHandler method");

    }
}

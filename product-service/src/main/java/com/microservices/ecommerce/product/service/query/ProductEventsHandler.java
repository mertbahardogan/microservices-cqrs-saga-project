package com.microservices.ecommerce.product.service.query;

import com.microservices.ecommerce.product.service.core.dataAccess.ProductDao;
import com.microservices.ecommerce.product.service.core.data.ProductEntity;
import com.microservices.ecommerce.product.service.core.events.ProductCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
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

    // Consume Command Event
    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        // Save Event through Entity to DB

        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(productCreatedEvent, productEntity);

        productDao.save(productEntity);
    }
}

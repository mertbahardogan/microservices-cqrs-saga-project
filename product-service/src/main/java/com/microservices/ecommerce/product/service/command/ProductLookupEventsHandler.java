package com.microservices.ecommerce.product.service.command;

import com.microservices.ecommerce.product.service.core.data.ProductLookupEntity;
import com.microservices.ecommerce.product.service.core.dataAccess.ProductLookupRepository;
import com.microservices.ecommerce.product.service.core.events.ProductCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
Resemble ProdcutEventsHandler. Just one difference is this class save Lookup DB instead of normal DB.
*/

@Component
@ProcessingGroup("product-group") // Processing Group is a logical way to group Event Handlers.
public class ProductLookupEventsHandler {

    private final ProductLookupRepository productLookupRepository;

    @Autowired
    public ProductLookupEventsHandler(ProductLookupRepository productLookupRepository) {
        this.productLookupRepository = productLookupRepository;
    }

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        ProductLookupEntity productLookupEntity = new ProductLookupEntity(productCreatedEvent.getProductId(), productCreatedEvent.getTitle());

        productLookupRepository.save(productLookupEntity);
    }
}
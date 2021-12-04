package com.microservices.ecommerce.product.service.queries;

import com.microservices.ecommerce.product.service.core.dataAccess.ProductDao;
import com.microservices.ecommerce.product.service.core.entities.ProductEntity;
import com.microservices.ecommerce.product.service.core.events.ProductCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ProductsEventHandler {
    private final ProductDao productDao;

    public ProductsEventHandler(ProductDao productDao) {
        this.productDao = productDao;
    }

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent){
        //SEARCH: bu method tam olarak ne yapıyor?   BeanUtils?
        ProductEntity productEntity=new ProductEntity();
        BeanUtils.copyProperties(productCreatedEvent,productEntity);

        productDao.save(productEntity);
    }
}

package com.microservices.ecommerce.product.service.query;

import com.microservices.ecommerce.product.service.core.dataAccess.ProductDao;
import com.microservices.ecommerce.product.service.core.entities.ProductEntity;
import com.microservices.ecommerce.product.service.query.models.ProductRequestModel;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductsQueryHandler {

    private final ProductDao productDao;

    public ProductsQueryHandler(ProductDao productDao) {
        this.productDao = productDao;
    }

    @QueryHandler
    public List<ProductRequestModel> findProducts(FindProductsQuery findProductsQuery) {
        List<ProductRequestModel> productRequestModels = new ArrayList<>();
        List<ProductEntity> storedProducts = productDao.findAll();
        for(ProductEntity productEntity:storedProducts){
            ProductRequestModel productRequestModel =new ProductRequestModel();
            BeanUtils.copyProperties(productEntity, productRequestModel);
            productRequestModels.add(productRequestModel);
        }
        return productRequestModels;
    }
}

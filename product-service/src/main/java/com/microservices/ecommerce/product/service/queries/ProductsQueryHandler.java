package com.microservices.ecommerce.product.service.queries;

import com.microservices.ecommerce.product.service.core.dataAccess.ProductDao;
import com.microservices.ecommerce.product.service.core.entities.ProductEntity;
import com.microservices.ecommerce.product.service.queries.models.ProductRestModel;
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
    public List<ProductRestModel> findProducts(FindProductsQuery findProductsQuery) {
        List<ProductRestModel> productRestModels = new ArrayList<>();
        List<ProductEntity> storedProducts = productDao.findAll();
        for(ProductEntity productEntity:storedProducts){
            ProductRestModel productRestModel=new ProductRestModel();
            BeanUtils.copyProperties(productEntity,productRestModel);
            productRestModels.add(productRestModel);
        }
        return productRestModels;
    }

}

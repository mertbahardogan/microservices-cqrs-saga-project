package com.microservices.ecommerce.product.service.queries.api;

import com.microservices.ecommerce.product.service.queries.FindProductsQuery;
import com.microservices.ecommerce.product.service.queries.models.ProductRequestModel;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsQueryController {

    @Autowired
    QueryGateway queryGateway;

    @GetMapping("all")
    public List<ProductRequestModel> getProducts() {
        FindProductsQuery findProductsQuery=new FindProductsQuery();
        List<ProductRequestModel> products= queryGateway.query(findProductsQuery, ResponseTypes.multipleInstancesOf(ProductRequestModel.class)).join();
        return products;
    }
}

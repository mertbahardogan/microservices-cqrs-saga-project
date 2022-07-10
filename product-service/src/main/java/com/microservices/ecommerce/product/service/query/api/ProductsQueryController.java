package com.microservices.ecommerce.product.service.query.api;

import com.microservices.ecommerce.product.service.query.FindProductsQuery;
import com.microservices.ecommerce.product.service.query.models.ProductRestModel;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsQueryController {

    private final QueryGateway queryGateway;

    @Autowired
    public ProductsQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @Autowired
    private Environment environment;

    @GetMapping
    public String getPortNumber() {
        return "HTTP GET WORKED AT: " + environment.getProperty("local.server.port");
    }

    @GetMapping("/all-products")
    public List<ProductRestModel> getProducts() {
        // For query parameter.
        FindProductsQuery findProductsQuery = new FindProductsQuery();
        // multipleInstancesOf returns all.
        List<ProductRestModel> products = queryGateway.query(findProductsQuery,
                ResponseTypes.multipleInstancesOf(ProductRestModel.class)).join();
        return products;
    }
}


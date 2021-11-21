package com.microservices.ecommerce.product.service.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class ProductsController {

    @GetMapping("products")
    public String products(){
        return "All products";
    }
}

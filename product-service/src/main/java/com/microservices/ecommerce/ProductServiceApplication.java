package com.microservices.ecommerce;

import com.microservices.ecommerce.product.service.command.interceptors.CreateProductCommandInterceptor;
import org.axonframework.commandhandling.CommandBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

@SpringBootApplication
@Configuration
public class ProductServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @Autowired
    public void registerCreateProductCommandInterceptor(ApplicationContext context, CommandBus commandBus) {
        commandBus.registerDispatchInterceptor(context.getBean(CreateProductCommandInterceptor.class));
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.microservices.ecommerce"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Spring Boot Simple REST API",
                "Spring Boot Simple REST API Swagger Documentation",
                "Version 1",
                "urn:tos",
                new Contact("Admin", "www.mertbahardogan.com", "info@mertbahardogan.com"),
                "Apache 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>());
    }
}
package com.microservices.ecommerce.swagger;

import java.util.ArrayList;
import java.util.List;

import com.microservices.ecommerce.config.SwaggerServicesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

@Component
@Primary
@Slf4j
public class SwaggerProvider implements SwaggerResourcesProvider {

    public static final String API_URI = "/v2/api-docs";

    private final RouteDefinitionLocator routeLocator;

    public SwaggerProvider(RouteDefinitionLocator routeLocator) {
        this.routeLocator = routeLocator;
    }

    @Autowired
    private SwaggerServicesConfig swaggerServiceList;

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();

        swaggerServiceList.getServices().forEach(service -> {
            resources.add(swaggerResource(service.getName(),service.getUrl()));
        });

//        routeLocator.getRouteDefinitions().subscribe(routeDefinition -> {
//            log.info("Discovered route definition: {}", routeDefinition.getId());
//            String resourceName = routeDefinition.getId();
//            System.out.println(routeDefinition.getPredicates().get(0).getArgs().get("pattern"));
//            String location = routeDefinition.getPredicates().get(0).getArgs().get("pattern").replace("/**", API_URI);
//            log.info("Adding swagger resource: {} with location {}", resourceName, location);
//            resources.add(swaggerResource(resourceName, location));
//        });


//        resources.add(swaggerResource("products-service", "/products-service/v2/api-docs"));
//          http://localhost:50675/v2/api-docs
//          TODO:buna dinamik olarak eriştiğimizde olacak gibi - daha sonra önüne ayrım amaçlı bir ad vererel urlleştirmemiz gerekir eğer çalışırsa.
        return resources;
    }

    //TODO: 9001 portu yerine load balance yapılarak dinamik port alınmalı.
    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
//        swaggerResource.setUrl("http://localhost:9001/v2/api-docs");
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
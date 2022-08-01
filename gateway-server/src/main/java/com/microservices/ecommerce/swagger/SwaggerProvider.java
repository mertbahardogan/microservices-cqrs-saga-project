package com.microservices.ecommerce.swagger;

import java.util.ArrayList;
import java.util.List;

import com.microservices.ecommerce.config.GatewayConfig;
import com.microservices.ecommerce.config.SwaggerServicesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

@Component
@Primary
@Slf4j
public class SwaggerProvider implements SwaggerResourcesProvider {

    //  TODO:buna dinamik olarak eriştiğimizde olacak gibi - daha sonra önüne ayrım amaçlı bir ad vererel urlleştirmemiz gerekir eğer çalışırsa.
    //  TODO: 9001 portu yerine load balance yapılarak dinamik port alınmalı. Bu ayağa kalkan lb alınarak yapılabilir.

    @Autowired
    private SwaggerServicesConfig swaggerServiceList;
//    private final GatewayConfig gatewayServiceList;
//
//    @Autowired
//    public SwaggerProvider(SwaggerServicesConfig swaggerServiceList, GatewayConfig gatewayServiceList) {
//        this.swaggerServiceList = swaggerServiceList;
//        this.gatewayServiceList = gatewayServiceList;
//    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();

        swaggerServiceList.getServices().forEach(service -> {
            resources.add(swaggerResource(service.getName(), service.getUrl(), service.getVersion()));
        });

//        gatewayServiceList.getServices().forEach(gatewayService -> {
//            System.out.println(gatewayService.getUri());
//        });
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String url, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setUrl(url);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}
package com.microservices.ecommerce.swagger;

import java.util.ArrayList;
import java.util.List;

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

    private final SwaggerServicesConfig swaggerServiceList;

    @Autowired
    public SwaggerProvider(SwaggerServicesConfig swaggerServiceList) {
        this.swaggerServiceList = swaggerServiceList;
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();

        swaggerServiceList.getServices().forEach(service -> {
            resources.add(swaggerResource(service.getName(), service.getUrl(), service.getVersion()));
        });

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
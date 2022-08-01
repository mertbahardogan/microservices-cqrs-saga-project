package com.microservices.ecommerce.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Primary
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "spring.cloud.gateway")
public class GatewayConfig {

    List<com.microservices.ecommerce.config.GatewayConfig.GatewayServices> gatewayList;

    public List<com.microservices.ecommerce.config.GatewayConfig.GatewayServices> getServices() {
        return gatewayList;
    }

    public void setServices(List<com.microservices.ecommerce.config.GatewayConfig.GatewayServices> gatewayResources) {
        this.gatewayList = gatewayResources;
    }

    @Component
    @EnableConfigurationProperties
    @ConfigurationProperties(prefix = "spring.cloud.gateway.routes")
    public static class GatewayServices {
        private String uri;

        public String getUri() {
            return uri;
        }
        public void setUri(String name) {
            this.uri = uri;
        }

    }
}

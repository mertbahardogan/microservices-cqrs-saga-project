package com.microservices.ecommerce.product.service.command.api;

import com.microservices.ecommerce.product.service.command.CreateProductCommand;
import com.microservices.ecommerce.product.service.command.models.CreateProductRestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.InetSocketAddress;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductsCommandController {

    private final CommandGateway commandGateway;

    @Autowired
    public ProductsCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String createProduct(@Valid @RequestBody CreateProductRestModel createProductRestModel) {

        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .price(createProductRestModel.getPrice())
                .quantity(createProductRestModel.getQuantity())
                .title(createProductRestModel.getTitle())
                .productId(UUID.randomUUID().toString()).build();

        String returnedValue;

        returnedValue = commandGateway.sendAndWait(createProductCommand);
        return returnedValue;
    }

    @GetMapping("/getBaseUrl")
    public ResponseEntity<String> getBaseUrl(@RequestHeader HttpHeaders headers) {
        InetSocketAddress host = headers.getHost();
        String url = "http://" + host.getHostName() + ":" + host.getPort();
        return new ResponseEntity<>(String.format("Base URL = %s", url), HttpStatus.OK);
        //Access: http://localhost:9001/products/getBaseUrl
    }
}
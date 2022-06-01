package com.microservices.ecommerce.product.service.command.api;

import com.microservices.ecommerce.product.service.command.CreateProductCommand;
import com.microservices.ecommerce.product.service.command.models.CreateProductRequestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping("products")
@RestController
public class ProductsCommandController {

    private final CommandGateway commandGateway;

    @Autowired
    public ProductsCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping()
    public String createProduct(@Valid @RequestBody CreateProductRequestModel createProductRequestModel) {

        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .price(createProductRequestModel.getPrice())
                .quantity(createProductRequestModel.getQuantity())
                .title(createProductRequestModel.getTitle())
                .productId(UUID.randomUUID().toString()).build();

        String returnedValue;

        try {
            // Send Command Object to Command Bus. sendAndWait wait for command to execute. Jump to Aggregate Class, ProductAggregate method.
            returnedValue = commandGateway.sendAndWait(createProductCommand);
        }catch (Exception e){
            returnedValue=e.getLocalizedMessage();
        }

        return returnedValue;
    }
}

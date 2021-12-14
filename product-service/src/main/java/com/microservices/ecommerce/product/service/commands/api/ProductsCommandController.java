package com.microservices.ecommerce.product.service.commands.api;

import com.microservices.ecommerce.product.service.commands.CreateProductCommand;
import com.microservices.ecommerce.product.service.commands.models.CreateProductRequestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("products")
@RestController
public class ProductsCommandController {

    private final CommandGateway commandGateway;

    @ConstructorBinding
    public ProductsCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @GetMapping()
    public String products() {
        return "All products";
    }

    @PostMapping()
    public String createProduct(@RequestBody CreateProductRequestModel createProductRequestModel) {
        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .price(createProductRequestModel.getPrice())
                .quantity(createProductRequestModel.getQuantity())
                .title(createProductRequestModel.getTitle())
                .productId(UUID.randomUUID().toString()).build();

        String returnedValue;
        try {
            returnedValue = commandGateway.sendAndWait(createProductCommand);
        }catch (Exception e){
            returnedValue=e.getLocalizedMessage();
        }

        return returnedValue;
    }
}

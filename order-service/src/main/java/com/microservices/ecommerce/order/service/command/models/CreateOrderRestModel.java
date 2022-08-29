package com.microservices.ecommerce.order.service.command.models;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class CreateOrderRestModel {

    @NotBlank(message = "Product ID is a required field.")
    private String productId;

    @Min(value = 0, message = "Quantity cannot be lower than 0.")
    private int quantity;

    @NotBlank(message = "Address ID is a required field.")
    private String addressId;
}

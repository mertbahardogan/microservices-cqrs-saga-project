package com.microservices.ecommerce.product.service.core.errorHandling;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ErrorMessage {

    private final Boolean isSuccess;
//    private final String errorTitle;  //TODO
    private final String errorMessage;
    private final String errorCode;
    private final Date timestamp;
}

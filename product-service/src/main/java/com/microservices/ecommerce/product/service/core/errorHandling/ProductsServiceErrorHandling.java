package com.microservices.ecommerce.product.service.core.errorHandling;

import com.microservices.ecommerce.product.service.command.interceptors.CreateProductCommandInterceptor;
import com.microservices.ecommerce.product.service.core.constants.ErrorCode;
import org.axonframework.commandhandling.CommandExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ProductsServiceErrorHandling {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateProductCommandInterceptor.class);

    @ExceptionHandler(value = {IllegalStateException.class})
    public ResponseEntity<Object> handleIllegalStateException(IllegalStateException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(false, "Illegal State Exception", ex.getMessage(), ErrorCode.ILLEGAL_STATE_EXCEPTION, new Date());
        LOGGER.error("An error occurred: " + errorResponse + " and request details: " + request.toString());
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(false, "Business Exception", ex.getMessage(), ErrorCode.OTHER_EXCEPTIONS, new Date());
        LOGGER.error("An error occurred: " + errorResponse + " and request details: " + request.toString());
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {CommandExecutionException.class})
    public ResponseEntity<Object> handleCommandExecutionException(CommandExecutionException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(false, "Command Execution Exception", ex.getMessage(), ErrorCode.COMMAND_EXECUTION_EXCEPTION, new Date());
        LOGGER.error("An error occurred: " + errorResponse + "and request details: " + request.toString());
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
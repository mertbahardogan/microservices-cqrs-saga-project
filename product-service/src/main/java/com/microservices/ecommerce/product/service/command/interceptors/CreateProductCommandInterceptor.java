package com.microservices.ecommerce.product.service.command.interceptors;

import com.microservices.ecommerce.product.service.command.CreateProductCommand;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;

/*
@Component:
We call the created CreateProductCommandInterceptor Bean as Spring Bean.
Other annotations can be thought of as extending this annotation. Used in places like util classes.
TODO: What is difference component and others?!
*/

/*
Message dispatch interceptors are invoked when a command is dispatched on a command bus.
They have the ability to alter the command message by adding metadata.
They can also block the command by throwing an exception.
These interceptors are always invoked on the thread that dispatches the command.
*/

@Component
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateProductCommandInterceptor.class);

    // Integer is Command Index, CommandMessage<?> is result of the function.
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(List<? extends CommandMessage<?>> list) {

        return (index, command) -> {

            LOGGER.info("Intercepted Command: " + command.getPayloadType());

            // Is equal CreateProductCommand class with command.getPayloadType?
            if (CreateProductCommand.class.equals(command.getPayloadType())) {

                CreateProductCommand createProductCommand = (CreateProductCommand) command.getPayload();

                // Validate CreateProductCommand
                if (createProductCommand.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                    throw new IllegalArgumentException("Price cannot be less or equal than zero");
                }
                if (createProductCommand.getTitle() == null || createProductCommand.getTitle().isBlank()) {
                    throw new IllegalArgumentException("Title cannot be empty");
                }
            }
            return command;
        };
    }
}

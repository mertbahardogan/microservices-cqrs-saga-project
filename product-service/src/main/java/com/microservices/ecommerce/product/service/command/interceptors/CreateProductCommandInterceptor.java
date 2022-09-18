package com.microservices.ecommerce.product.service.command.interceptors;

import com.microservices.ecommerce.product.service.command.CreateProductCommand;
import com.microservices.ecommerce.product.service.core.data.ProductLookupEntity;
import com.microservices.ecommerce.product.service.core.dataAccess.ProductLookupRepository;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;

/*
@Component:
We call the created CreateProductCommandInterceptor Bean as Spring Bean.
Other annotations can be thought of as extending this annotation. Used in places like util classes.

*/

/*
Message dispatch interceptors are invoked when a command is dispatched on a command bus.
They have the ability to alter the command message by adding metadata.
They can also block the command by throwing an exception.
These interceptors are always invoked on the thread that dispatches the command.

Interceptor is between of the CommandGateway (sendAndWait after there) and the Command Bus.
*/

@Component
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private final ProductLookupRepository productLookupRepository;

    @Autowired
    public CreateProductCommandInterceptor(ProductLookupRepository productLookupRepository) {
        this.productLookupRepository = productLookupRepository;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateProductCommandInterceptor.class);

    // Integer is Command Index, CommandMessage<?> is result of the function.
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(List<? extends CommandMessage<?>> list) {

        return (index, command) -> {
            LOGGER.info("Intercepted Command: " + command.getPayloadType());

            // Is equal CreateProductCommand class with command.getPayloadType?
            if (CreateProductCommand.class.equals(command.getPayloadType())) {
                CreateProductCommand createProductCommand = (CreateProductCommand) command.getPayload();

                ProductLookupEntity productLookupEntity = productLookupRepository.findByProductIdOrTitle(createProductCommand.getProductId(),
                        createProductCommand.getTitle());

                if (productLookupEntity != null) {
                    throw new IllegalStateException(String.format("Product with productId %s or title %s already exists.",
                            createProductCommand.getProductId(), createProductCommand.getTitle()));
                }
            }
            return command;
        };
    }
}

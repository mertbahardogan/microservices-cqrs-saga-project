package com.microservices.ecommerce.order.service.query;

import com.microservices.ecommerce.order.service.core.data.OrderEntity;
import com.microservices.ecommerce.order.service.core.dataAccess.OrdersRepository;
import com.microservices.ecommerce.order.service.core.events.OrderCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("order-group")
public class OrderEventsHandler {
    private final OrdersRepository ordersRepository;

    @Autowired
    public OrderEventsHandler(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @EventHandler
    public void on(OrderCreatedEvent orderCreatedEvent) {
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(orderCreatedEvent, orderEntity);

        ordersRepository.save(orderEntity);
    }
}
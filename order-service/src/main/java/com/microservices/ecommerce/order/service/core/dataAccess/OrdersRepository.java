package com.microservices.ecommerce.order.service.core.dataAccess;

import com.microservices.ecommerce.order.service.core.data.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<OrderEntity,String> {
}

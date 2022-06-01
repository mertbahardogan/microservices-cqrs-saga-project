package com.microservices.ecommerce.product.service.core.dataAccess;

import com.microservices.ecommerce.product.service.core.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends JpaRepository<ProductEntity, String> {

    ProductEntity findByProductId(String productId);
    ProductEntity findByProductIdOrTitle(String productId,String title);

}

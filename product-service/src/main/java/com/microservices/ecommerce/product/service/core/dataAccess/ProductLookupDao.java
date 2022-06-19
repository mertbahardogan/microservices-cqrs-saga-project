package com.microservices.ecommerce.product.service.core.dataAccess;

import com.microservices.ecommerce.product.service.core.data.ProductLookupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductLookupDao extends JpaRepository<ProductLookupEntity,String> {
    ProductLookupEntity findByProductIdOrTitle(String productId,String title);
}

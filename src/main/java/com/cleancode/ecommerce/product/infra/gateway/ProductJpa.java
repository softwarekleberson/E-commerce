package com.cleancode.ecommerce.product.infra.gateway;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cleancode.ecommerce.product.infra.persistence.product.ProductEntity;

public interface ProductJpa extends JpaRepository<ProductEntity, String> {

	Page<ProductEntity> findByActiveTrue(Pageable pageable);

	Page<ProductEntity> findByActiveFalse(Pageable pageable);

	Optional<ProductEntity> findById(String productId);

}
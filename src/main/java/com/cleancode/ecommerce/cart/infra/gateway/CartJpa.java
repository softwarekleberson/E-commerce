package com.cleancode.ecommerce.cart.infra.gateway;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cleancode.ecommerce.cart.infra.persistence.CartEntity;

public interface CartJpa extends JpaRepository<CartEntity, String>{

	@Query("SELECT c FROM CartEntity c WHERE c.customer_id = :customerId")
	Optional<CartEntity> findByCustomerId(@Param("customerId") String customerId);
}

package com.cleancode.ecommerce.cart.infra.gateway;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cleancode.ecommerce.cart.infra.persistence.CartEntity;

public interface CartJpa extends JpaRepository<CartEntity, String>{

	@Query("SELECT c FROM CartEntity c WHERE c.customer_id = :customerId")
	Optional<CartEntity> findByCustomerId(@Param("customerId") String customerId);

	@Query("""
			SELECT DISTINCT c
			FROM CartEntity c
			LEFT JOIN FETCH c.cart_itens
			WHERE (c.updated_at <= :cutoff OR (c.updated_at IS NULL AND c.created_at <= :cutoff))
			  AND c.cart_itens IS NOT EMPTY
			""")
	List<CartEntity> findExpiredCarts(@Param("cutoff") LocalDateTime cutoff);
}

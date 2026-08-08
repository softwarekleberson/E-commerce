package com.cleancode.ecommerce.customer.infra.gateway;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cleancode.ecommerce.customer.infra.persistence.customer.CustomerEntity;

public interface CustomerJpa extends JpaRepository<CustomerEntity, String> {

	@Query("""
			    SELECT c
			    FROM CustomerEntity c
			    LEFT JOIN FETCH c.chargeEntities ch
			    LEFT JOIN FETCH c.deliveryEntities de
			    WHERE c.id = :customerId
			""")
	Optional<CustomerEntity> findFullById(@Param("customerId") String customerId);

	@Query("""
			    SELECT c
			    FROM CustomerEntity c
			""")
	Page<CustomerEntity> findAllCustomer(Pageable pageable);

	Optional<CustomerEntity> findByEmail_Email(String email);
}

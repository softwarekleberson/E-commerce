package com.cleancode.ecommerce.order.infra.gateway;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cleancode.ecommerce.order.infra.persistencia.OrderEntity;

public interface OrderJpa extends JpaRepository<OrderEntity, String>{

	@Query(
	        value = """
	            SELECT DISTINCT o 
	            FROM OrderEntity o 
	            LEFT JOIN FETCH o.order_itens 
	            WHERE o.customer_Id = :customerId
	        """,
	        countQuery = """
	            SELECT COUNT(o) 
	            FROM OrderEntity o 
	            WHERE o.customer_Id = :customerId
	        """
	    )
	    Page<OrderEntity> findByCustomerId(
	        @Param("customerId") String customerId, 
	        Pageable pageable
	    );
}

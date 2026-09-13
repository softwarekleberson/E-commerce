package com.cleancode.ecommerce.order.infra.gateway;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cleancode.ecommerce.order.infra.persistencia.ItemStatusEntity;
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
	
	@Query("SELECT o FROM OrderEntity o LEFT JOIN FETCH o.order_itens WHERE o.order_Id = :orderId")
    Optional<OrderEntity> findByIdWithItems(@Param("orderId") String orderId);

	
	@Query("SELECT DISTINCT o FROM OrderEntity o JOIN o.order_itens i WHERE i.itemStatus = :status")
	Page<OrderEntity> findByItemStatus(@Param("status") ItemStatusEntity status, Pageable pageable);
	
	
	@Query("SELECT i.price FROM OrderEntity o JOIN o.order_itens i WHERE i.reservation_id = :reservationId")
    Optional<BigDecimal> findSubtotalByReservationId(@Param("reservationId") String reservationId);
}

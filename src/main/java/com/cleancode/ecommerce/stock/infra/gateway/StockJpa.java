package com.cleancode.ecommerce.stock.infra.gateway;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cleancode.ecommerce.stock.infra.persistence.StockEntity;

public interface StockJpa extends JpaRepository<StockEntity, String> {

	Optional<StockEntity> findByProductId(String id);

	@Query("""
			    SELECT s
			    FROM StockEntity s
			    INNER JOIN FETCH s.reservations r
			    WHERE s.stock_id = :stockId
			""")
	Optional<StockEntity> findByStockIdWithReservations(@Param("stockId") String stockId);

	@Query("""
			    SELECT r.stock
			    FROM ReservationEntity r
			    LEFT JOIN FETCH r.stock.inputs
			    WHERE r.reservation_id = :reservationId
			""")
	Optional<StockEntity> findStockByReservationId(@Param("reservationId") String reservationId);
}

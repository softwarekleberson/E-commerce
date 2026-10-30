package com.cleancode.ecommerce.stock.infra.gateway;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cleancode.ecommerce.stock.infra.persistence.StockEntity;
import jakarta.persistence.LockModeType;

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

	@Query("SELECT DISTINCT r.stock.stock_id FROM ReservationEntity r WHERE r.reservation_id IN :reservationIds")
	List<String> findStockIdsByReservationIds(@Param("reservationIds") Collection<String> reservationIds);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT s FROM StockEntity s WHERE s.stock_id IN :stockIds ORDER BY s.stock_id")
	List<StockEntity> lockStocksByIds(@Param("stockIds") Collection<String> stockIds);

	@Query("SELECT DISTINCT s FROM StockEntity s LEFT JOIN FETCH s.reservations WHERE s.stock_id IN :stockIds")
	List<StockEntity> fetchReservationsByStockIds(@Param("stockIds") Collection<String> stockIds);

	@Query("SELECT DISTINCT s FROM StockEntity s LEFT JOIN FETCH s.inputs WHERE s.stock_id IN :stockIds")
	List<StockEntity> fetchInputsByStockIds(@Param("stockIds") Collection<String> stockIds);

	@Query("SELECT DISTINCT s FROM StockEntity s LEFT JOIN FETCH s.outputs WHERE s.stock_id IN :stockIds")
	List<StockEntity> fetchOutputsByStockIds(@Param("stockIds") Collection<String> stockIds);
}

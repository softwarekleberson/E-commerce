package com.cleancode.ecommerce.stock.domain.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.cleancode.ecommerce.stock.domain.Stock;

public interface StockRepository {

	Stock save(Stock stoke);
	List<Stock> saveAll(Collection<Stock> stocks);
	Optional <Stock> getStock (String id);
	Optional<Stock> findByStock (String id);
	Optional <Stock> findStockByReservationId (String reservationId);
	List<Stock> findStocksByReservationIds(Collection<String> reservationIds);
}

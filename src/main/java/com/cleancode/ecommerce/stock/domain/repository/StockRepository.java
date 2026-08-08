package com.cleancode.ecommerce.stock.domain.repository;

import java.util.Optional;

import com.cleancode.ecommerce.stock.domain.Stock;

public interface StockRepository {

	Stock save(Stock stoke);
	Optional <Stock> getStock (String id);
	Optional<Stock> findByStock (String id);
	Optional <Stock> findStockByReservationId (String reservationId);
}

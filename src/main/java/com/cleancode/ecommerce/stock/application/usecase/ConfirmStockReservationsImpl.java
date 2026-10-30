package com.cleancode.ecommerce.stock.application.usecase;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.cleancode.ecommerce.shared.exception.IllegalDomainException;
import com.cleancode.ecommerce.stock.application.dto.StockReservationConfirmation;
import com.cleancode.ecommerce.stock.application.usecase.contract.ConfirmStockReservations;
import com.cleancode.ecommerce.stock.domain.Stock;
import com.cleancode.ecommerce.stock.domain.exception.IllegalReservationException;
import com.cleancode.ecommerce.stock.domain.repository.StockRepository;

public class ConfirmStockReservationsImpl implements ConfirmStockReservations {

	private final StockRepository stockRepository;

	public ConfirmStockReservationsImpl(StockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}

	@Override
	@Transactional
	public void execute(Collection<StockReservationConfirmation> confirmations) {
		if (confirmations.isEmpty()) {
			return;
		}

		Map<String, String> productIdByReservationId = new LinkedHashMap<>();
		for (StockReservationConfirmation confirmation : confirmations) {
			if (confirmation == null || confirmation.reservationId() == null || confirmation.reservationId().isBlank()
					|| confirmation.productId() == null || confirmation.productId().isBlank()) {
				throw new IllegalDomainException("Reservation and product IDs are required");
			}
			String previousProductId = productIdByReservationId.putIfAbsent(confirmation.reservationId(),
					confirmation.productId());
			if (previousProductId != null && !previousProductId.equals(confirmation.productId())) {
				throw new IllegalDomainException("Reservation cannot refer to multiple products");
			}
		}

		Map<String, Stock> stockByReservationId = new LinkedHashMap<>();
		for (Stock stock : stockRepository.findStocksByReservationIds(productIdByReservationId.keySet())) {
			stock.getReservations().forEach(reservation -> stockByReservationId.put(reservation.getReservationId(), stock));
		}

		for (Map.Entry<String, String> entry : productIdByReservationId.entrySet()) {
			Stock stock = stockByReservationId.get(entry.getKey());
			if (stock == null) {
				throw new IllegalReservationException("Reservation not found");
			}
			if (!stock.getProductId().getProductId().equals(entry.getValue())) {
				throw new IllegalReservationException("Reservation does not belong to the requested product");
			}
		}

		LinkedHashSet<Stock> stocksToSave = new LinkedHashSet<>();
		for (Map.Entry<String, String> entry : productIdByReservationId.entrySet()) {
			Stock stock = stockByReservationId.get(entry.getKey());
			stock.confirmOrder(entry.getValue(), entry.getKey());
			stocksToSave.add(stock);
		}

		stockRepository.saveAll(stocksToSave);
	}
}

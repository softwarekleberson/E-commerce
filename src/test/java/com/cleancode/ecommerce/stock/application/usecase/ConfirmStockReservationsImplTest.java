package com.cleancode.ecommerce.stock.application.usecase;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.cleancode.ecommerce.product.domain.ProductId;
import com.cleancode.ecommerce.stock.application.dto.StockReservationConfirmation;
import com.cleancode.ecommerce.stock.domain.Stock;
import com.cleancode.ecommerce.stock.domain.exception.IllegalReservationException;
import com.cleancode.ecommerce.stock.domain.repository.StockRepository;
import com.cleancode.ecommerce.stock.domain.reservation.Reservations;

class ConfirmStockReservationsImplTest {

	private StockRepository stockRepository;
	private ConfirmStockReservationsImpl confirmStockReservations;
	private Stock stock;

	@BeforeEach
	void setUp() {
		stockRepository = mock(StockRepository.class);
		confirmStockReservations = new ConfirmStockReservationsImpl(stockRepository);
		stock = mock(Stock.class);

		ProductId productId = mock(ProductId.class);
		when(productId.getProductId()).thenReturn("product-1");
		when(stock.getProductId()).thenReturn(productId);

		Reservations firstReservation = reservation("reservation-1");
		Reservations secondReservation = reservation("reservation-2");
		when(stock.getReservations()).thenReturn(List.of(firstReservation, secondReservation));
		when(stockRepository.findStocksByReservationIds(anyCollection())).thenReturn(List.of(stock));
	}

	@Test
	void confirmsAllReservationsAndSavesEachStockOnce() {
		confirmStockReservations.execute(List.of(
				new StockReservationConfirmation("reservation-1", "product-1"),
				new StockReservationConfirmation("reservation-2", "product-1")));

		verify(stock).confirmOrder("product-1", "reservation-1");
		verify(stock).confirmOrder("product-1", "reservation-2");
		verify(stockRepository).saveAll(argThat(stocks -> stocks.size() == 1 && stocks.contains(stock)));
	}

	@Test
	void missingReservationPreventsAllConfirmationAndPersistence() {
		assertThrows(IllegalReservationException.class, () -> confirmStockReservations.execute(List.of(
				new StockReservationConfirmation("reservation-1", "product-1"),
				new StockReservationConfirmation("missing-reservation", "product-1"))));

		verify(stock, never()).confirmOrder("product-1", "reservation-1");
		verify(stockRepository, never()).saveAll(anyCollection());
	}

	@Test
	void confirmationFailurePreventsPersistence() {
		doThrow(new IllegalReservationException("Reservation cannot be confirmed"))
				.when(stock).confirmOrder("product-1", "reservation-2");

		assertThrows(IllegalReservationException.class, () -> confirmStockReservations.execute(List.of(
				new StockReservationConfirmation("reservation-1", "product-1"),
				new StockReservationConfirmation("reservation-2", "product-1"))));

		verify(stockRepository, never()).saveAll(anyCollection());
	}

	private Reservations reservation(String id) {
		Reservations reservation = mock(Reservations.class);
		when(reservation.getReservationId()).thenReturn(id);
		return reservation;
	}
}

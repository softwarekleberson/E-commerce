package com.cleancode.ecommerce.cart.application.service;

import com.cleancode.ecommerce.cart.application.dtos.input.CartUpdateStockResult;
import com.cleancode.ecommerce.cart.application.service.contract.CancelProductStockReservation;
import com.cleancode.ecommerce.cart.application.service.contract.CartUpdateIntegrationService;
import com.cleancode.ecommerce.cart.application.service.contract.UpdateNewReservation;
import com.cleancode.ecommerce.customer.domain.customer.repository.CustomerRepository;
import com.cleancode.ecommerce.shared.exception.IllegalDomainException;
import com.cleancode.ecommerce.stock.domain.Stock;
import com.cleancode.ecommerce.stock.domain.repository.StockRepository;
import com.cleancode.ecommerce.stock.domain.reservation.ReserveStatus;

public class CartUpdateIntegrationServiceImpl implements CartUpdateIntegrationService{

	private final CustomerRepository customerRepository;
	private final StockRepository stockRepository;
	private final CancelProductStockReservation cancelService;
	private final UpdateNewReservation updateNewReservation;

	public CartUpdateIntegrationServiceImpl(
			CustomerRepository customerRepository, 
			StockRepository stockRepository,
			CancelProductStockReservation cancelService, 
			UpdateNewReservation updateNewReservation) {
		this.customerRepository = customerRepository;
		this.stockRepository = stockRepository;
		this.cancelService = cancelService;
		this.updateNewReservation = updateNewReservation;
	}

	@Override
	public String resolveCustomerIdByEmail(String email) {
		return customerRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalDomainException("Customer not found with email: " + email))
				.getId().getValue();
	}

	@Override
	public CartUpdateStockResult cycleProductReservation(String currentReservationId, int newQuantity, String customerId, String cartId) {
		
		Stock stock = stockRepository.findStockByReservationId(currentReservationId)
				.orElseThrow(() -> new IllegalArgumentException("Reservation id not found: " + currentReservationId));

		Stock stockAfterCancel = cancelService.cancel(stock, currentReservationId);

		Stock stockAfterReservation = updateNewReservation.creteNewReservation(
				stockAfterCancel, newQuantity, customerId, cartId
		);

		var novaReserva = stockAfterReservation.getReservations().stream()

				.filter(r -> r.getCartId().getCartId().equals(cartId)) 
		        .filter(r -> r.getReserveStatus() == ReserveStatus.ACTIVE)
		        .filter(r -> !r.getReservationId().equals(currentReservationId)) 
		        .findFirst() 
		        .orElseThrow(() -> new IllegalDomainException(
		                "Nova reserva ativa não encontrada no agregado de estoque para o Cart ID: " + cartId
		        ));

		stockRepository.save(stockAfterReservation);

		return new CartUpdateStockResult(novaReserva.getReservationId());
	}
}

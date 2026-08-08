package com.cleancode.ecommerce.cart.application.service;

import com.cleancode.ecommerce.cart.application.service.contract.CartRemovalIntegrationService;
import com.cleancode.ecommerce.cart.application.service.dto.CancelReservationResult;
import com.cleancode.ecommerce.customer.domain.customer.repository.CustomerRepository;
import com.cleancode.ecommerce.shared.exception.IllegalDomainException;
import com.cleancode.ecommerce.stock.domain.Stock;
import com.cleancode.ecommerce.stock.domain.repository.StockRepository;

public class CartRemovalIntegrationServiceImpl implements CartRemovalIntegrationService{

	private final CustomerRepository customerRepository;
    private final StockRepository stockRepository;

    public CartRemovalIntegrationServiceImpl(CustomerRepository customerRepository, StockRepository stockRepository) {
        this.customerRepository = customerRepository;
        this.stockRepository = stockRepository;
    }

    @Override
    public String resolveCustomerIdByEmail(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalDomainException("Customer not found with email: " + email))
                .getId().getValue();
    }

    @Override
    public CancelReservationResult releaseStockReservation(String reservationId) {
        Stock stock = stockRepository.findStockByReservationId(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation id not found: " + reservationId));
        
        stock.cancelReservation(reservationId);        
        stockRepository.save(stock);

        return new CancelReservationResult(reservationId);
    }
}

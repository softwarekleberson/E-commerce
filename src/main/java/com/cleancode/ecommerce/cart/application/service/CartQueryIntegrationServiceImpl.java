package com.cleancode.ecommerce.cart.application.service;

import com.cleancode.ecommerce.cart.application.service.contract.CartQueryIntegrationService;
import com.cleancode.ecommerce.customer.domain.customer.repository.CustomerRepository;
import com.cleancode.ecommerce.shared.exception.IllegalDomainException;

public class CartQueryIntegrationServiceImpl implements CartQueryIntegrationService{

	private final CustomerRepository customerRepository;

    public CartQueryIntegrationServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public String resolveCustomerIdByEmail(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalDomainException("Customer not found with email: " + email))
                .getId().getValue();
    }
}
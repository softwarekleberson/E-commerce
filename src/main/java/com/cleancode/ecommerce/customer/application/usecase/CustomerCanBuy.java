package com.cleancode.ecommerce.customer.application.usecase;

import com.cleancode.ecommerce.customer.application.usecase.contract.CustomerIsActive;
import com.cleancode.ecommerce.customer.domain.customer.Customer;
import com.cleancode.ecommerce.customer.domain.customer.repository.CustomerRepository;
import com.cleancode.ecommerce.shared.exception.CustomerNotFoundException;

public class CustomerCanBuy implements CustomerIsActive{

	private final CustomerRepository repository;

	public CustomerCanBuy(CustomerRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public void customerIsActive(String email) {
		Customer customerActivet =  repository.findByEmail(email)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found") );
	
		boolean isInactive = customerActivet.getSystemClientStatus();
		if(isInactive == false) {
			throw new CustomerNotFoundException("The customer is inactive in the system and cannot perform transactions.");
		}
	}
}

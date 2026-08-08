package com.cleancode.ecommerce.customer.application.usecase;

import com.cleancode.ecommerce.customer.application.usecase.contract.DeleteDelivery;
import com.cleancode.ecommerce.customer.domain.customer.Customer;
import com.cleancode.ecommerce.customer.domain.customer.repository.CustomerRepository;
import com.cleancode.ecommerce.shared.exception.CustomerNotFoundException;

public class DeleteDeliveryImpl implements DeleteDelivery {

	private final CustomerRepository repository;

	public DeleteDeliveryImpl(CustomerRepository repository) {
		this.repository = repository;
	}

	@Override
	public void execute(String email, String deliveryId) {
		Customer customer = repository.findByEmail(email).orElseThrow(()-> new CustomerNotFoundException("Customer not found")); 
		customer.removeDeliveryById(deliveryId);
		
		repository.save(customer);
	}
}
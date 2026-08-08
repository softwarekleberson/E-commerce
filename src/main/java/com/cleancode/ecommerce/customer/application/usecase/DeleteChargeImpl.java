package com.cleancode.ecommerce.customer.application.usecase;

import com.cleancode.ecommerce.customer.application.usecase.contract.DeleteCharge;
import com.cleancode.ecommerce.customer.domain.customer.Customer;
import com.cleancode.ecommerce.customer.domain.customer.repository.CustomerRepository;
import com.cleancode.ecommerce.shared.exception.CustomerNotFoundException;

public class DeleteChargeImpl implements DeleteCharge{

	private final CustomerRepository repository;

	public DeleteChargeImpl(CustomerRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public void execute(String email, String ChargeId) {
		Customer customer = repository.findByEmail(email).orElseThrow(()-> new CustomerNotFoundException("Customer not found")); 
		customer.removeChargeById(ChargeId);
		
		repository.save(customer);
	}
}
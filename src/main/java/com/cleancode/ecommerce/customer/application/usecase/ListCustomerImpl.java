package com.cleancode.ecommerce.customer.application.usecase;

import com.cleancode.ecommerce.customer.application.dtos.customer.ListCustomerDto;
import com.cleancode.ecommerce.customer.application.usecase.contract.ListCustomer;
import com.cleancode.ecommerce.customer.domain.customer.repository.CustomerRepository;
import com.cleancode.ecommerce.shared.exception.CustomerNotFoundException;

public class ListCustomerImpl implements ListCustomer {

	private final CustomerRepository repository;

	public ListCustomerImpl(CustomerRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public ListCustomerDto execute(String email) {
		return new ListCustomerDto(repository.findByEmail(email).orElseThrow(()-> new CustomerNotFoundException("Customer with not found"))); 
	}
}
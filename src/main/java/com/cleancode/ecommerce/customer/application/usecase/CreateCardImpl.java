package com.cleancode.ecommerce.customer.application.usecase;

import com.cleancode.ecommerce.customer.application.dtos.card.CreateCardDto;
import com.cleancode.ecommerce.customer.application.dtos.customer.ListCustomerDto;
import com.cleancode.ecommerce.customer.application.usecase.contract.CreateCustomerCard;
import com.cleancode.ecommerce.customer.domain.customer.Customer;
import com.cleancode.ecommerce.customer.domain.customer.repository.CustomerRepository;
import com.cleancode.ecommerce.shared.exception.CustomerNotFoundException;

public class CreateCardImpl implements CreateCustomerCard {

	private final CustomerRepository repository;

	public CreateCardImpl(CustomerRepository repository) {
		this.repository = repository;
	}

	@Override
	public ListCustomerDto execute(String email, CreateCardDto dto) {
		
		Customer customer = repository.findByEmail(email)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

		customer.registerCard(
	            dto.isMain(),
	            dto.getPrintedName(),
	            dto.getCode(),
	            dto.getNumberCard(),
	            dto.getExpirationDate(),
	            dto.getFlag()
	        );

		repository.save(customer);
		return new ListCustomerDto(customer);
	}
}
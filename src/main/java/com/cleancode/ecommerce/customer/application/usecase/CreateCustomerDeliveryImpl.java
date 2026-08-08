package com.cleancode.ecommerce.customer.application.usecase;

import com.cleancode.ecommerce.customer.application.dtos.address.CreateDeliveryDto;
import com.cleancode.ecommerce.customer.application.dtos.customer.ListCustomerDto;
import com.cleancode.ecommerce.customer.application.usecase.contract.CreateCustomerDelivery;
import com.cleancode.ecommerce.customer.domain.customer.Customer;
import com.cleancode.ecommerce.customer.domain.customer.repository.CustomerRepository;
import com.cleancode.ecommerce.shared.exception.CustomerNotFoundException;

public class CreateCustomerDeliveryImpl implements CreateCustomerDelivery{

	private final CustomerRepository repository;

	public CreateCustomerDeliveryImpl(CustomerRepository repository) {
		this.repository = repository;
	}
	
	public ListCustomerDto execute(String email, CreateDeliveryDto dto) {
		Customer customer = repository.findByEmail(email).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));				
		
		customer.registerDelivery(
				dto.getReceiver(),
				dto.getMain(),
				dto.getStreet(),
				dto.getNumber(),
				dto.getNeighborhood(),
				dto.getZipCode(),
				dto.getObservation(),
				dto.getStreetType(),
				dto.getTypeResidence(),
				dto.getCity(),
				dto.getState(),
				dto.getCountry(),
				dto.getDeliveryPhrase()
		);
		
		repository.save(customer);
		return new ListCustomerDto(customer);
	}
}
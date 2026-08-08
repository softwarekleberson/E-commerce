package com.cleancode.ecommerce.customer.application.usecase;

import com.cleancode.ecommerce.customer.application.dtos.address.CreateChargeDto;
import com.cleancode.ecommerce.customer.application.dtos.customer.ListCustomerDto;
import com.cleancode.ecommerce.customer.application.usecase.contract.CreateCustomerCharge;
import com.cleancode.ecommerce.customer.domain.customer.Customer;
import com.cleancode.ecommerce.customer.domain.customer.repository.CustomerRepository;
import com.cleancode.ecommerce.shared.exception.CustomerNotFoundException;

public class CreateCustomerChargeImpl implements CreateCustomerCharge{

	private final CustomerRepository repository;
	
	public CreateCustomerChargeImpl(CustomerRepository repository) {
		this.repository = repository;
	}
	
	public ListCustomerDto execute(String email, CreateChargeDto dto) {
		Customer customer = repository.findByEmail(email).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
		
		customer.registerCharge
				 (dto.getReceiver(),
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
				  dto.getCountry());
		
		repository.save(customer);
		return new ListCustomerDto(customer);
	}
}
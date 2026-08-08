package com.cleancode.ecommerce.customer.application.dtos.customer;

import java.time.LocalDate;

import com.cleancode.ecommerce.customer.domain.contact.Phone;
import com.cleancode.ecommerce.customer.domain.customer.Customer;
import com.cleancode.ecommerce.customer.domain.customer.Gender;
import com.cleancode.ecommerce.shared.kernel.Email;

public record ListAllCustomersDto(

		String id, boolean systemClientStatus, LocalDate birth, String name, Gender gender, Email email, Phone phone

) {

	public ListAllCustomersDto(Customer customer) {
		this(customer.getId().getValue(),
			 customer.getSystemClientStatus(),
			 customer.getBirth().getBirth(),
			 customer.getName().getName(),
			 customer.getGender(),
			 customer.getContact().getEmail(),
			 customer.getContact().getFullPhone()
		);
	}
}

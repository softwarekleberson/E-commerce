package com.cleancode.ecommerce.customer.application.usecase;

import java.util.UUID;

import com.cleancode.ecommerce.customer.application.dtos.customer.CreateCustomerDto;
import com.cleancode.ecommerce.customer.application.dtos.customer.ListCustomerDto;
import com.cleancode.ecommerce.customer.application.usecase.contract.CreateCustomer;
import com.cleancode.ecommerce.customer.application.usecase.contract.PasswordValidationCheck;
import com.cleancode.ecommerce.customer.domain.contact.Contact;
import com.cleancode.ecommerce.customer.domain.contact.Phone;
import com.cleancode.ecommerce.customer.domain.customer.Birth;
import com.cleancode.ecommerce.customer.domain.customer.Customer;
import com.cleancode.ecommerce.customer.domain.customer.CustomerId;
import com.cleancode.ecommerce.customer.domain.customer.SystemClientStatus;
import com.cleancode.ecommerce.customer.domain.customer.repository.CustomerRepository;
import com.cleancode.ecommerce.shared.kernel.Cpf;
import com.cleancode.ecommerce.shared.kernel.Email;
import com.cleancode.ecommerce.shared.kernel.Name;
import com.cleancode.ecommerce.shared.kernel.Password;
import com.cleancode.ecommerce.shared.util.passwordencoder.contract.Passwordencoder;

public class CreateCustomerImpl implements CreateCustomer {

	private final CustomerRepository repository;
	private final PasswordValidationCheck passwordValidation;
    private final Passwordencoder encryptPassword;

	public CreateCustomerImpl(CustomerRepository repository, PasswordValidationCheck passwordValidation,
							  Passwordencoder encryptPassword) {
		this.repository = repository;
		this.passwordValidation = passwordValidation;
		this.encryptPassword = encryptPassword;
	}

	public ListCustomerDto execute(CreateCustomerDto dto) {
		
		passwordValidation.passwordCheckAndConfirmPassword(dto.getPassword(), dto.getConfirmPassword());
		passwordValidation.validateAcceptablePasswordFormat(dto.getPassword());
		String passwordEncode = encryptPassword.execute(dto.getPassword());
		
		Customer customer = new Customer
		(new CustomerId(UUID.randomUUID().toString()),
		 new Name(dto.getName()),
		 dto.getGender(),
		 new Birth(dto.getBirth()),
		 new Cpf(dto.getCpf()),
		 new Contact(new Phone(dto.getDdd(), dto.getPhone(), dto.getTypePhone()), new Email(dto.getEmail())),
		 new Password(passwordEncode),
		 new SystemClientStatus(true));
		
		repository.save(customer);
		return new ListCustomerDto(customer);
	}
}
package com.cleancode.ecommerce.customer.application.usecase;

import com.cleancode.ecommerce.customer.application.dtos.customer.UpdatePasswordDto;
import com.cleancode.ecommerce.customer.application.usecase.contract.PasswordValidationCheck;
import com.cleancode.ecommerce.customer.application.usecase.contract.UpdatePassword;
import com.cleancode.ecommerce.customer.domain.customer.Customer;
import com.cleancode.ecommerce.customer.domain.customer.repository.CustomerRepository;
import com.cleancode.ecommerce.shared.exception.CustomerNotFoundException;
import com.cleancode.ecommerce.shared.util.passwordencoder.contract.Passwordencoder;

public class UpdatePasswordImpl implements UpdatePassword {

	private final CustomerRepository repository;
    private final Passwordencoder encryptPassword;
	private final PasswordValidationCheck passwordValidation;

	public UpdatePasswordImpl(CustomerRepository repository, Passwordencoder encryptPassword, PasswordValidationCheck passwordValidation) {
		this.repository = repository;
		this.encryptPassword = encryptPassword;
		this.passwordValidation = passwordValidation;
	}

	@Override
	public void execute(String email, UpdatePasswordDto dto) {
		Customer customer = repository.findByEmail(email)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

		passwordValidation.passwordCheckAndConfirmPassword(dto.password(), dto.confirmPassword());
		passwordValidation.validateAcceptablePasswordFormat(dto.password());
		
		String passwordEncode = encryptPassword.execute(dto.password());
		customer.updatePassword(passwordEncode);
		repository.save(customer);
	}
}
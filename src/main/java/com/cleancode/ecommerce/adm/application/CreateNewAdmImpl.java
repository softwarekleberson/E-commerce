package com.cleancode.ecommerce.adm.application;

import com.cleancode.ecommerce.adm.application.dto.CreateAdmDto;
import com.cleancode.ecommerce.adm.domain.Adm;
import com.cleancode.ecommerce.adm.domain.repository.AdmRepository;
import com.cleancode.ecommerce.customer.application.usecase.contract.PasswordValidationCheck;
import com.cleancode.ecommerce.shared.kernel.Email;
import com.cleancode.ecommerce.shared.kernel.Password;
import com.cleancode.ecommerce.shared.util.passwordencoder.contract.Passwordencoder;

public class CreateNewAdmImpl implements CreateNewAdm {

	private final AdmRepository repository;
	private final Passwordencoder encryptPassword;
	private final PasswordValidationCheck passwordValidation;
	
	public CreateNewAdmImpl(AdmRepository repository, Passwordencoder encryptPassword, PasswordValidationCheck passwordValidation) {
		this.repository = repository;
		this.encryptPassword = encryptPassword;
		this.passwordValidation = passwordValidation;
	}
	
	@Override
	public void execute(CreateAdmDto dto) {
		
		passwordValidation.validateAcceptablePasswordFormat(dto.getPassword());
		String passwordEncode = encryptPassword.execute(dto.getPassword());
		
		Adm adm = new Adm(
			    new Email(dto.getEmail()), 
			    new Password(passwordEncode)
		);
		
		repository.save(adm);
	}
}

package com.cleancode.ecommerce.adm.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cleancode.ecommerce.adm.application.CreateNewAdm;
import com.cleancode.ecommerce.adm.application.CreateNewAdmImpl;
import com.cleancode.ecommerce.adm.domain.repository.AdmRepository;
import com.cleancode.ecommerce.customer.application.usecase.contract.PasswordValidationCheck;
import com.cleancode.ecommerce.shared.util.passwordencoder.contract.Passwordencoder;

@Configuration
public class AdmConfig {

	@Bean
	public CreateNewAdm createNewAdm (AdmRepository repository, Passwordencoder encryptPassword, PasswordValidationCheck passwordValidation) {
		return new CreateNewAdmImpl(repository, encryptPassword, passwordValidation); 
	}
}

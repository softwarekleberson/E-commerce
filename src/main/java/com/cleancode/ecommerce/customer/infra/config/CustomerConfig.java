package com.cleancode.ecommerce.customer.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cleancode.ecommerce.customer.application.usecase.ChangeActivationStatusAdmImpl;
import com.cleancode.ecommerce.customer.application.usecase.CreateCardImpl;
import com.cleancode.ecommerce.customer.application.usecase.CreateCustomerChargeImpl;
import com.cleancode.ecommerce.customer.application.usecase.CreateCustomerDeliveryImpl;
import com.cleancode.ecommerce.customer.application.usecase.CreateCustomerImpl;
import com.cleancode.ecommerce.customer.application.usecase.DeleteChargeImpl;
import com.cleancode.ecommerce.customer.application.usecase.DeleteDeliveryImpl;
import com.cleancode.ecommerce.customer.application.usecase.ListAllCustomersImpl;
import com.cleancode.ecommerce.customer.application.usecase.ListCustomerImpl;
import com.cleancode.ecommerce.customer.application.usecase.PasswordValidationCheckImpl;
import com.cleancode.ecommerce.customer.application.usecase.UpdateChargeImpl;
import com.cleancode.ecommerce.customer.application.usecase.UpdateCustomerImpl;
import com.cleancode.ecommerce.customer.application.usecase.UpdateDeliveryImpl;
import com.cleancode.ecommerce.customer.application.usecase.UpdatePasswordImpl;
import com.cleancode.ecommerce.customer.application.usecase.contract.ChangeActivationStatusAdm;
import com.cleancode.ecommerce.customer.application.usecase.contract.CreateCustomer;
import com.cleancode.ecommerce.customer.application.usecase.contract.CreateCustomerCard;
import com.cleancode.ecommerce.customer.application.usecase.contract.CreateCustomerCharge;
import com.cleancode.ecommerce.customer.application.usecase.contract.CreateCustomerDelivery;
import com.cleancode.ecommerce.customer.application.usecase.contract.DeleteCharge;
import com.cleancode.ecommerce.customer.application.usecase.contract.DeleteDelivery;
import com.cleancode.ecommerce.customer.application.usecase.contract.ListAllCustomers;
import com.cleancode.ecommerce.customer.application.usecase.contract.ListCustomer;
import com.cleancode.ecommerce.customer.application.usecase.contract.PasswordValidationCheck;
import com.cleancode.ecommerce.customer.application.usecase.contract.UpdateCharge;
import com.cleancode.ecommerce.customer.application.usecase.contract.UpdateCustomer;
import com.cleancode.ecommerce.customer.application.usecase.contract.UpdateDelivery;
import com.cleancode.ecommerce.customer.application.usecase.contract.UpdatePassword;
import com.cleancode.ecommerce.customer.domain.customer.repository.CustomerRepository;
import com.cleancode.ecommerce.shared.util.passwordencoder.EncryptPasswordImpl;
import com.cleancode.ecommerce.shared.util.passwordencoder.contract.PasswordEncoderService;
import com.cleancode.ecommerce.shared.util.passwordencoder.contract.Passwordencoder;

@Configuration
public class CustomerConfig {

	@Bean
	public CreateCustomer createCustomer(CustomerRepository repository, PasswordValidationCheck passwordValidation,
			Passwordencoder encryptPassword) {
		return new CreateCustomerImpl(repository, passwordValidation, encryptPassword);
	}
	
	@Bean
	public Passwordencoder encryptPassword (PasswordEncoderService passwordEncoderService) {
		return new EncryptPasswordImpl(passwordEncoderService);
	}
		
	@Bean
	public CreateCustomerCard createCustomerCard(CustomerRepository repository) {
		return new CreateCardImpl(repository);
	}
	
	@Bean
	public PasswordValidationCheck passwordValidation() {
		return new PasswordValidationCheckImpl();
	}

	@Bean
	public CreateCustomerDelivery createCustomerDelivery(CustomerRepository repository) {
		return new CreateCustomerDeliveryImpl(repository);
	}

	@Bean
	public CreateCustomerCharge createCustomerCharge(CustomerRepository repository) {
		return new CreateCustomerChargeImpl(repository);
	}

	@Bean
	public ListCustomer listCustomer(CustomerRepository repository) {
		return new ListCustomerImpl(repository);
	}
	
	@Bean
	public ListAllCustomers listAllCustomers(CustomerRepository repository) {
		return new ListAllCustomersImpl(repository);
	}

	@Bean
	public UpdateCustomer updateCustomer(CustomerRepository repository) {
		return new UpdateCustomerImpl(repository);
	}

	@Bean
	public UpdatePassword updatePassword(CustomerRepository repository, Passwordencoder encryptPassword, PasswordValidationCheck passwordValidation) {
		return new UpdatePasswordImpl(repository, encryptPassword, passwordValidation);
	}

	@Bean
	public DeleteCharge deleteCharge(CustomerRepository repository) {
		return new DeleteChargeImpl(repository);
	}

	@Bean
	public DeleteDelivery deleteDelivery(CustomerRepository repository) {
		return new DeleteDeliveryImpl(repository);
	}

	@Bean
	public UpdateCharge updateCharge(CustomerRepository repositor) {
		return new UpdateChargeImpl(repositor);
	}

	@Bean
	public UpdateDelivery updateDelivery(CustomerRepository repositor) {
		return new UpdateDeliveryImpl(repositor);
	}
	
	@Bean
	public ChangeActivationStatusAdm changeActivationStatusAdm(CustomerRepository repositor) {
		return new ChangeActivationStatusAdmImpl(repositor);
	}
}
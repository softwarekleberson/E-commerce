package com.cleancode.ecommerce.promotional.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cleancode.ecommerce.customer.domain.customer.repository.CustomerRepository;
import com.cleancode.ecommerce.promotional.domain.repository.VoucherRepository;
import com.cleancode.ecommerce.promotional.usecase.CreateVoucherImpl;
import com.cleancode.ecommerce.promotional.usecase.ListVoucherCustomerImpl;
import com.cleancode.ecommerce.promotional.usecase.ReplacementVoucherImpl;
import com.cleancode.ecommerce.promotional.usecase.contract.CreateVoucher;
import com.cleancode.ecommerce.promotional.usecase.contract.ListVoucherCustomer;
import com.cleancode.ecommerce.promotional.usecase.contract.ReplacementVoucher;
import com.cleancode.ecommerce.promotional.usecase.service.contract.CustomerIdentityService;

@Configuration
public class VoucherConfig {

	@Bean
	public CreateVoucher createVoucher(VoucherRepository repository, CustomerIdentityService customerIdentityService) {
		return new CreateVoucherImpl(repository, customerIdentityService);
	}
	
	@Bean
	public ListVoucherCustomer listVoucherCustomer (VoucherRepository repository, CustomerRepository customerRepository) {
		return new ListVoucherCustomerImpl(repository, customerRepository); 
	}
	
	@Bean
	public ReplacementVoucher replacementVoucher (VoucherRepository repository, CustomerIdentityService service) {
		return new ReplacementVoucherImpl(repository, service);
	}
}

package com.cleancode.ecommerce.voucher.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cleancode.ecommerce.customer.domain.customer.repository.CustomerRepository;
import com.cleancode.ecommerce.voucher.domain.repository.VoucherRepository;
import com.cleancode.ecommerce.voucher.usecase.CreateVoucherImpl;
import com.cleancode.ecommerce.voucher.usecase.ListVoucherCustomerImpl;
import com.cleancode.ecommerce.voucher.usecase.ReplacementVoucherImpl;
import com.cleancode.ecommerce.voucher.usecase.contract.CreateVoucher;
import com.cleancode.ecommerce.voucher.usecase.contract.ListVoucherCustomer;
import com.cleancode.ecommerce.voucher.usecase.contract.ReplacementVoucher;
import com.cleancode.ecommerce.voucher.usecase.service.contract.CustomerIdentityService;

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

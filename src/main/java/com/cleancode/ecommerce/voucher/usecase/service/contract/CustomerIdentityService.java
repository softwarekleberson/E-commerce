package com.cleancode.ecommerce.voucher.usecase.service.contract;

public interface CustomerIdentityService {
	boolean existsById(String customerId);
}

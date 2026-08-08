package com.cleancode.ecommerce.promotional.usecase.service.contract;

public interface CustomerIdentityService {
	boolean existsById(String customerId);
}

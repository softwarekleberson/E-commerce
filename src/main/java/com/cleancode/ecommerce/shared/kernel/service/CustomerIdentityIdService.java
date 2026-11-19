package com.cleancode.ecommerce.order.application.service;

import java.util.Optional;

public interface CustomerIdentityIdService {

	Optional<String> customerId(String email);
}

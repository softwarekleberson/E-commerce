package com.cleancode.ecommerce.shared.kernel.service;

import java.util.Optional;

public interface CustomerIdentityIdService {

	Optional<String> customerIdentityIdService(String email);
}

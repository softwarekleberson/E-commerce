package com.cleancode.ecommerce.replacement.application.service;

import java.util.Optional;

public interface FindOrderByIdService {

	public Optional<String> findCustomerIdByOrderId(String orderId);
	boolean isItemDelivered(String reservationId);

}

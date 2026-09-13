package com.cleancode.ecommerce.order.infra.persistencia;

public enum ItemStatusEntity {

	AWAITING_PAYMENT,
	CANCELLED,
	SEPARATING,
	SHIPPED,
	DELIVERED,
	EXCHANGE_REQUEST,
	EXCHANGE_ACCEPTED,
	EXCHANGE_REJECTED
}

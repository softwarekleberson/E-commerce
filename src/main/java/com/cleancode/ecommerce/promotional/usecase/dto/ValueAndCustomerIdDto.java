package com.cleancode.ecommerce.promotional.usecase.dto;

import java.math.BigDecimal;

public record ValueAndCustomerIdDto(String customerId, BigDecimal value) {

	public ValueAndCustomerIdDto(String customerId, BigDecimal value) {
		this.customerId = customerId;
		this.value = value;
	}
}

package com.cleancode.ecommerce.cart.application.service.dto;

import java.math.BigDecimal;

import com.cleancode.ecommerce.shared.kernel.TypeCoin;

public record ContextDetailsDto(
		
		String customerId,
	    String productId,
	    String productName,
	    String productImageUrl,
	    BigDecimal price,
	    TypeCoin coin
		
								)
{}

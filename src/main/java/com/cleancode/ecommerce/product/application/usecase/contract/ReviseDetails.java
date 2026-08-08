package com.cleancode.ecommerce.product.application.usecase.contract;

import com.cleancode.ecommerce.product.application.dto.input.ReviseDetailsDto;

public interface ReviseDetails {

	public void execute(String productId, ReviseDetailsDto dto);
}

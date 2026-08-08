package com.cleancode.ecommerce.cart.application.usecase.contract;

import com.cleancode.ecommerce.cart.application.dtos.input.DeleteUniqueProductToCartDto;

public interface DeleteUniqueProductCart {

	void execute(String email, String cartItemId, DeleteUniqueProductToCartDto dto);
}

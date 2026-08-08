package com.cleancode.ecommerce.cart.application.usecase.contract;

import com.cleancode.ecommerce.cart.application.dtos.input.UpdateCartDto;
import com.cleancode.ecommerce.cart.application.dtos.output.CartDto;

public interface UpdateCart {

	CartDto execute(String email, String cartItemId, UpdateCartDto dto);
}

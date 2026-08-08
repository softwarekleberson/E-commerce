package com.cleancode.ecommerce.cart.application.usecase.contract;

import com.cleancode.ecommerce.cart.application.dtos.input.CreateCartDto;
import com.cleancode.ecommerce.cart.application.dtos.output.CartDto;

public interface AddProductToCart {

	CartDto execute (CreateCartDto dto);
}

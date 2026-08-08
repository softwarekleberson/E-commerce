package com.cleancode.ecommerce.cart.application.usecase.contract;

import com.cleancode.ecommerce.cart.application.dtos.output.CartDto;

public interface ListCart {

	CartDto execute (String email);
}

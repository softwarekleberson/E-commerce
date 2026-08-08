package com.cleancode.ecommerce.customer.application.usecase.contract;

import com.cleancode.ecommerce.customer.application.dtos.customer.UpdatePasswordDto;

public interface UpdatePassword {

	public void execute (String email, UpdatePasswordDto dto);
}

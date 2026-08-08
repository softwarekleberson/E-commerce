package com.cleancode.ecommerce.customer.application.usecase.contract;

import com.cleancode.ecommerce.customer.application.dtos.address.UpdateAddressDto;
import com.cleancode.ecommerce.customer.application.dtos.customer.ListCustomerDto;

public interface UpdateCharge {

	public ListCustomerDto execute(String email, String id, UpdateAddressDto dto);
}

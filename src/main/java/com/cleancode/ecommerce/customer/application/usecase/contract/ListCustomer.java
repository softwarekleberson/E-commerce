package com.cleancode.ecommerce.customer.application.usecase.contract;

import com.cleancode.ecommerce.customer.application.dtos.customer.ListCustomerDto;

public interface ListCustomer {

	public ListCustomerDto execute(String email);
}

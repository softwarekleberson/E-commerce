package com.cleancode.ecommerce.customer.application.usecase.contract;

import com.cleancode.ecommerce.customer.application.dtos.customer.CreateCustomerDto;
import com.cleancode.ecommerce.customer.application.dtos.customer.ListCustomerDto;

public interface CreateCustomer {

	ListCustomerDto execute(CreateCustomerDto dto);

}

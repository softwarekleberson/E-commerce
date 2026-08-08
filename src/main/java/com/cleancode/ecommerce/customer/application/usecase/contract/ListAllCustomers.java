package com.cleancode.ecommerce.customer.application.usecase.contract;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cleancode.ecommerce.customer.application.dtos.customer.ListAllCustomersDto;

public interface ListAllCustomers {

	Page<ListAllCustomersDto> execute(Pageable pageable);
}

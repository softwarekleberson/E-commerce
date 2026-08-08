package com.cleancode.ecommerce.promotional.usecase.contract;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cleancode.ecommerce.promotional.usecase.dto.ListVoucherDto;

public interface ListVoucherCustomer {

	public Page<ListVoucherDto> execute(String email, Pageable pageable);
}

package com.cleancode.ecommerce.order.application.usecase.contract;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cleancode.ecommerce.order.application.usecase.dto.ListOrdersDto;

public interface ListAllOrder {

	public Page<ListOrdersDto> execute(Pageable pageable);
}

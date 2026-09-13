package com.cleancode.ecommerce.order.application.usecase.order.contract;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cleancode.ecommerce.order.application.usecase.order.dto.ListOrdersDto;

public interface ListAllOrder {

	public Page<ListOrdersDto> execute(Pageable pageable);
}

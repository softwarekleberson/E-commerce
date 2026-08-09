package com.cleancode.ecommerce.order.application.usecase.contract;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cleancode.ecommerce.order.application.usecase.dto.ListOrdersDto;

public interface ListOrders {

	Page<ListOrdersDto> execute (String email, Pageable pageable);
}

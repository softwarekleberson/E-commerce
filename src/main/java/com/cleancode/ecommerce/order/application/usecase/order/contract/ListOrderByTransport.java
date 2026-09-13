package com.cleancode.ecommerce.order.application.usecase.order.contract;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cleancode.ecommerce.order.application.usecase.order.dto.ListOrdersDto;
import com.cleancode.ecommerce.order.domain.state.itens.ItemStatus;

public interface ListOrderByTransport {

	public Page<ListOrdersDto> execute(ItemStatus status, Pageable pageable);
}

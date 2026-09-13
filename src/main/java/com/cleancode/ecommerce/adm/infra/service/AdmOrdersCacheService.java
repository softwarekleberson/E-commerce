package com.cleancode.ecommerce.adm.infra.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cleancode.ecommerce.order.application.usecase.order.contract.ListAllOrder;
import com.cleancode.ecommerce.order.application.usecase.order.dto.ListOrdersDto;
import com.cleancode.ecommerce.shared.util.pagination.controller.PageResponse;

@Service
public class AdmOrdersCacheService {

	private final ListAllOrder listAllOrders;

	public AdmOrdersCacheService(ListAllOrder listAllOrders) {
		this.listAllOrders = listAllOrders;
	}

	@Cacheable(
			value = "adm-orders",
			key = "'page:' + #pageable.pageNumber + ':size:' + #pageable.pageSize + ':sort:' + #pageable.sort"
	)
	public PageResponse<ListOrdersDto> getOrders(Pageable pageable) {
		var result = listAllOrders.execute(pageable);
		return PageResponse.from(result);
	}
}

package com.cleancode.ecommerce.adm.infra.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cleancode.ecommerce.adm.application.order.contract.ListAdmOrders;
import com.cleancode.ecommerce.adm.application.order.dto.AdmOrderDto;
import com.cleancode.ecommerce.shared.util.pagination.controller.PageResponse;

@Service
public class AdmOrdersCacheService {

	private final ListAdmOrders listAdmOrders;

	public AdmOrdersCacheService(ListAdmOrders listAdmOrders) {
		this.listAdmOrders = listAdmOrders;
	}

	public PageResponse<AdmOrderDto> getOrders(Pageable pageable) {
		var result = listAdmOrders.execute(pageable);
		return PageResponse.from(result);
	}
}

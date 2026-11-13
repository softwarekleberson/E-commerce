package com.cleancode.ecommerce.adm.application.order.contract;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cleancode.ecommerce.adm.application.order.dto.AdmOrderDto;

public interface ListAdmOrders {
	Page<AdmOrderDto> execute(Pageable pageable);
}

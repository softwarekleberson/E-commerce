package com.cleancode.ecommerce.adm.infra.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cleancode.ecommerce.order.application.usecase.contract.ListAllOrder;
import com.cleancode.ecommerce.order.application.usecase.dto.ListOrdersDto;
import com.cleancode.ecommerce.shared.util.pagination.controller.PageResponse;

@RestController
@RequestMapping("/adm/orders")
@CrossOrigin(origins = "*")
public class AdmOrdersController {

	private final ListAllOrder listAllOrders;
	
	public AdmOrdersController(ListAllOrder listAllOrders) {
		this.listAllOrders = listAllOrders;
	}
	
	@GetMapping
	public ResponseEntity<PageResponse<ListOrdersDto>> getAllOrders(
			@PageableDefault(page = 0, size = 10) Pageable pageable) {
		
		var result = listAllOrders.execute(pageable);
		return ResponseEntity.ok(PageResponse.from(result));
	}
}
package com.cleancode.ecommerce.order.infra.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cleancode.ecommerce.order.application.usecase.item.contract.CancelledOrder;
import com.cleancode.ecommerce.order.application.usecase.order.contract.ListOrders;
import com.cleancode.ecommerce.order.application.usecase.order.dto.ListOrdersDto;
import com.cleancode.ecommerce.shared.util.pagination.controller.PageResponse;

@RestController
@RequestMapping("/customer/orders")
@CrossOrigin(origins = "*")
public class OrderController {

	private final ListOrders listOrders;
	private final CancelledOrder cancelledOrder;
	
	public OrderController(ListOrders listOrders, CancelledOrder cancelledOrder) {
		this.listOrders = listOrders;
		this.cancelledOrder = cancelledOrder;
	}
	
	@PutMapping("/{orderId}/cancelled/{reservationId}")
	public ResponseEntity<Void> cancelledOrder(Authentication authentication, @PathVariable String orderId, @PathVariable String reservationId) {
		cancelledOrder.execute(orderId, reservationId);
		
		if (authentication == null || !authentication.isAuthenticated()
				|| authentication instanceof AnonymousAuthenticationToken) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public ResponseEntity<PageResponse<ListOrdersDto>> getOrders (Authentication authentication,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
		
		if (authentication == null || !authentication.isAuthenticated()
				|| authentication instanceof AnonymousAuthenticationToken) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		String email = authentication.getName();
		
		Pageable pageable = PageRequest.of(page, size);
		var result = listOrders.execute(email, pageable);
		return ResponseEntity.ok(PageResponse.from(result));
	}
}

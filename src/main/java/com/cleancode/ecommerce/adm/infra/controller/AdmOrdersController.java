package com.cleancode.ecommerce.adm.infra.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cleancode.ecommerce.adm.infra.service.AdmOrdersCacheService;
import com.cleancode.ecommerce.order.application.usecase.item.contract.DeliveredOrder;
import com.cleancode.ecommerce.order.application.usecase.item.contract.TransitOrder;
import com.cleancode.ecommerce.order.application.usecase.order.contract.ListOrderByTransport;
import com.cleancode.ecommerce.order.application.usecase.order.dto.ListOrdersDto;
import com.cleancode.ecommerce.order.domain.state.itens.ItemStatus;
import com.cleancode.ecommerce.shared.util.pagination.controller.PageResponse;

@RestController
@RequestMapping("/adm/orders")
@CrossOrigin(origins = "*")
public class AdmOrdersController {

	private final AdmOrdersCacheService ordersCacheService;
	private final DeliveredOrder deliveredOrder;
	private final TransitOrder transitOrder;
	private final ListOrderByTransport listOrderByTransport;
	
	public AdmOrdersController(AdmOrdersCacheService ordersCacheService, DeliveredOrder deliveredOrder, TransitOrder transitOrder, ListOrderByTransport listOrderByTransport) {
		this.ordersCacheService = ordersCacheService;
		this.deliveredOrder = deliveredOrder;
		this.transitOrder = transitOrder;
		this.listOrderByTransport = listOrderByTransport;
	}
	
	@PutMapping("/{orderId}/transit/{reservationId}")
	public ResponseEntity<Void> transitOrder(@PathVariable String orderId, @PathVariable String reservationId) {
		transitOrder.execute(orderId, reservationId);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{orderId}/delivery/{reservationId}")
	public ResponseEntity<Void> deliverOrder(@PathVariable String orderId, @PathVariable String reservationId) {
		deliveredOrder.execute(orderId, reservationId);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public ResponseEntity<PageResponse<ListOrdersDto>> getAllOrders(
			@PageableDefault(page = 0, size = 10) Pageable pageable) {
		
		return ResponseEntity.ok(ordersCacheService.getOrders(pageable));
	}
	
	@GetMapping("/itens/delivery")
	public ResponseEntity<Page<ListOrdersDto>> getOrdersTransport(
	        @RequestParam(required = false) ItemStatus status,
	        @PageableDefault(page = 0, size = 10) Pageable pageable) {
	    
	    return ResponseEntity.ok(listOrderByTransport.execute(status, pageable));
	}
}
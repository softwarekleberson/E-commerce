package com.cleancode.ecommerce.replacement.application.usecase;

import com.cleancode.ecommerce.customer.domain.customer.CustomerId;
import com.cleancode.ecommerce.event.ExchangeRequestAfterReview.EventExchangePublish;
import com.cleancode.ecommerce.event.ExchangeRequestAfterReview.ExchangeEvent;
import com.cleancode.ecommerce.order.application.service.CustomerIdentityIdService;
import com.cleancode.ecommerce.order.domain.Order;
import com.cleancode.ecommerce.order.domain.OrderId;
import com.cleancode.ecommerce.order.domain.exceptions.IllegalDomainOrder;
import com.cleancode.ecommerce.replacement.application.dto.CreateReplacementDto;
import com.cleancode.ecommerce.replacement.application.service.FindOrderByIdService;
import com.cleancode.ecommerce.replacement.application.usecase.contract.CreateReplacement;
import com.cleancode.ecommerce.replacement.domain.Explain;
import com.cleancode.ecommerce.replacement.domain.Replacement;
import com.cleancode.ecommerce.replacement.domain.exception.IllegalReplacementException;
import com.cleancode.ecommerce.replacement.domain.Quantity;
import com.cleancode.ecommerce.replacement.domain.repository.ReplacementRepository;
import com.cleancode.ecommerce.stock.domain.reservation.ReservationId;

public class CreateReplacementImp implements CreateReplacement {

	private final ReplacementRepository repository;
	private final FindOrderByIdService findOrderById;
	private final EventExchangePublish eventPublish;
	private final CustomerIdentityIdService customerIdentity;
	
	public CreateReplacementImp(ReplacementRepository repository, FindOrderByIdService findOrderById,
			EventExchangePublish eventPublish, CustomerIdentityIdService customerIdentity) {
		this.repository = repository;
		this.findOrderById = findOrderById;
		this.eventPublish = eventPublish;
		this.customerIdentity = customerIdentity;
	}
	
	public void execute (CreateReplacementDto dto, String customerEmail) {
		String customerId = customerIdentity.customerId(customerEmail)
				.orElseThrow(() -> new IllegalDomainOrder("Order not found"));
		
		Order order = findOrderById.findOrderById(dto.getOrderId())
				.orElseThrow(() -> new IllegalDomainOrder("Order not found"));
		if (!order.getCustomerId().getValue().equals(customerId)) {
			throw new IllegalDomainOrder("Order not found");
		}
		
		boolean itemDelivered = order.itemdelivered(dto.getReservationId());
		
		if(!itemDelivered) {
			throw new IllegalReplacementException("The item must be delivered first before requesting an exchange.");
		}
		
		Replacement replacement = new Replacement(
				new OrderId(dto.getOrderId()),
			    new ReservationId(dto.getReservationId()),
			    dto.getReason(),
			    new Explain(dto.getExplain()),
			    new CustomerId(customerId),
			    new Quantity(dto.getQuantity())
		);
		
		repository.save(replacement);
		eventPublish.publish(new ExchangeEvent(replacement.getOrderId().getOrderId(), replacement.getReservationId().getReservationId()));
	}
}

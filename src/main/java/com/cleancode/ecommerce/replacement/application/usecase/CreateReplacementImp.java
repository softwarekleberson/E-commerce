package com.cleancode.ecommerce.replacement.application.usecase;

import com.cleancode.ecommerce.event.exchange.EventExchangePublish;
import com.cleancode.ecommerce.event.exchange.ExchangeEvent;
import com.cleancode.ecommerce.replacement.application.dto.CreateReplacementDto;
import com.cleancode.ecommerce.replacement.application.service.FindOrderByIdService;
import com.cleancode.ecommerce.replacement.application.usecase.contract.CreateReplacement;
import com.cleancode.ecommerce.replacement.domain.Explain;
import com.cleancode.ecommerce.replacement.domain.Replacement;
import com.cleancode.ecommerce.replacement.domain.exception.IllegalReplacementException;
import com.cleancode.ecommerce.replacement.domain.Quantity;
import com.cleancode.ecommerce.replacement.domain.repository.ReplacementRepository;
import com.cleancode.ecommerce.shared.kernel.domain.CustomerId;
import com.cleancode.ecommerce.shared.kernel.domain.OrderId;
import com.cleancode.ecommerce.shared.kernel.domain.ReservationId;
import com.cleancode.ecommerce.shared.kernel.exception.OrderNotFoundException;

public class CreateReplacementImp implements CreateReplacement {

	private final ReplacementRepository repository;
	private final FindOrderByIdService findOrderById;
	private final EventExchangePublish eventPublish;
	
	public CreateReplacementImp(ReplacementRepository repository, FindOrderByIdService findOrderById,
			EventExchangePublish eventPublish) {
		this.repository = repository;
		this.findOrderById = findOrderById;
		this.eventPublish = eventPublish;
	}
	
	public void execute (CreateReplacementDto dto, String customerEmail) {
		
		String orderCustomerId = findOrderById.findCustomerIdByOrderId(dto.getOrderId())
				.orElseThrow(() -> new OrderNotFoundException("customer id not found on Order"));
			
		boolean itemDelivered = findOrderById.isItemDelivered(dto.getReservationId());
		
		if(!itemDelivered) {
			throw new IllegalReplacementException("The item must be delivered before an exchange is requested, or while the item is undergoing an exchange review.");
		}
		
		Replacement replacement = new Replacement(
				new OrderId(dto.getOrderId()),
			    new ReservationId(dto.getReservationId()),
			    dto.getReason(),
			    new Explain(dto.getExplain()),
			    new CustomerId(orderCustomerId),
			    new Quantity(dto.getQuantity())
		);
		
		repository.save(replacement);
		eventPublish.publish(new ExchangeEvent(replacement.getOrderId().getOrderId(), replacement.getReservationId().getReservationId()));
	}
}

package com.cleancode.ecommerce.replacement.application.usecase;

import java.math.BigDecimal;

import com.cleancode.ecommerce.event.replacement.EventReplacementPublisher;
import com.cleancode.ecommerce.event.replacement.ReplacementEvent;
import com.cleancode.ecommerce.replacement.application.service.ValueUnitProductService;
import com.cleancode.ecommerce.replacement.application.usecase.contract.AcceptReplacement;
import com.cleancode.ecommerce.replacement.domain.Replacement;
import com.cleancode.ecommerce.replacement.domain.exception.IllegalReplacementException;
import com.cleancode.ecommerce.replacement.domain.repository.ReplacementRepository;

public class AcceptReplacementImp implements AcceptReplacement{

	private final ReplacementRepository repository;
	private final ValueUnitProductService service;
	private final EventReplacementPublisher publisher;

	public AcceptReplacementImp(ReplacementRepository repository, ValueUnitProductService service, EventReplacementPublisher publisher) {
		this.repository = repository;
		this.service = service;
		this.publisher = publisher;
	}
	
	@Override
	public void execute (String reservationId) {
		Replacement replacement = repository.getReplacementById(reservationId)
				.orElseThrow(() -> new IllegalReplacementException("Replacement not found by id : " + reservationId));
		
		BigDecimal value = service.findSubtotalByReservationId(reservationId)
				.orElseThrow(() -> new IllegalReplacementException("Unit price product not find in order : " + reservationId));
		
		var replacementAccept = replacement.accept();
		var valueVoucher = replacement.exchangeVoucherValue(value);
		repository.save(replacementAccept);
		
		publisher.publish(new ReplacementEvent(replacementAccept.getCustomerId().getValue(), valueVoucher));
	}
}
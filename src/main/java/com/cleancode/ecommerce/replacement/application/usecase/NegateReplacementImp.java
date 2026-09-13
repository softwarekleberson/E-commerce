package com.cleancode.ecommerce.replacement.application.usecase;

import com.cleancode.ecommerce.event.ExchangeRequestAfterReview.ExchangeEventAfterReviewAdm;
import com.cleancode.ecommerce.event.ExchangeRequestAfterReview.SpringEventExchangePublisher;
import com.cleancode.ecommerce.replacement.application.usecase.contract.NegateReplacement;
import com.cleancode.ecommerce.replacement.domain.Replacement;
import com.cleancode.ecommerce.replacement.domain.exception.IllegalReplacementException;
import com.cleancode.ecommerce.replacement.domain.repository.ReplacementRepository;

public class NegateReplacementImp implements NegateReplacement{

	private final ReplacementRepository repository;
	private final SpringEventExchangePublisher exchangePublisher;

	public NegateReplacementImp(ReplacementRepository repository, SpringEventExchangePublisher exchangePublisher) {
		this.repository = repository;
		this.exchangePublisher = exchangePublisher;
	}
	
	@Override
	public void execute (String reservationId) {
		Replacement replacement = repository.getReplacementById(reservationId)
				.orElseThrow(() -> new IllegalReplacementException("Replacement not found by id : " + reservationId));
		
		var replacementNegate = replacement.negate();
		repository.save(replacementNegate);
		exchangePublisher.publish(new ExchangeEventAfterReviewAdm(replacement.getOrderId().getOrderId(), reservationId, false));
	}
}
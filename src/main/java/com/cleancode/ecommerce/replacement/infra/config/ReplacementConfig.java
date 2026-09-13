package com.cleancode.ecommerce.replacement.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cleancode.ecommerce.event.ExchangeRequestAfterReview.EventExchangePublish;
import com.cleancode.ecommerce.event.ExchangeRequestAfterReview.SpringEventExchangePublisher;
import com.cleancode.ecommerce.event.replacement.EventReplacementPublisher;
import com.cleancode.ecommerce.replacement.application.service.FindOrderByIdService;
import com.cleancode.ecommerce.replacement.application.service.ValueUnitProductService;
import com.cleancode.ecommerce.replacement.application.usecase.AcceptReplacementImp;
import com.cleancode.ecommerce.replacement.application.usecase.CreateReplacementImp;
import com.cleancode.ecommerce.replacement.application.usecase.ListReplacementImpl;
import com.cleancode.ecommerce.replacement.application.usecase.NegateReplacementImp;
import com.cleancode.ecommerce.replacement.application.usecase.contract.AcceptReplacement;
import com.cleancode.ecommerce.replacement.application.usecase.contract.CreateReplacement;
import com.cleancode.ecommerce.replacement.application.usecase.contract.ListReplacement;
import com.cleancode.ecommerce.replacement.application.usecase.contract.NegateReplacement;
import com.cleancode.ecommerce.replacement.domain.repository.ReplacementRepository;

@Configuration
public class ReplacementConfig {

	@Bean
	public CreateReplacement createReplacement (ReplacementRepository repository, FindOrderByIdService findOrderById,EventExchangePublish eventPublish) {
		return new CreateReplacementImp(repository, findOrderById, eventPublish);
	}
	
	@Bean
	public NegateReplacement negateReplacement (ReplacementRepository repository, SpringEventExchangePublisher exchangePublisher) {
		return new NegateReplacementImp(repository, exchangePublisher);
	}
	
	@Bean
	public AcceptReplacement acceptReplacement (ReplacementRepository repository, ValueUnitProductService service, EventReplacementPublisher publisher, SpringEventExchangePublisher exchangePublisher) {
		return new AcceptReplacementImp(repository, service, publisher, exchangePublisher);
	}
	
	@Bean
	public ListReplacement listReplacement (ReplacementRepository repository) {
		return new ListReplacementImpl(repository);
	}
}
package com.cleancode.ecommerce.replacement.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cleancode.ecommerce.event.replacement.EventReplacementPublisher;
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
	public CreateReplacement createReplacement (ReplacementRepository repository) {
		return new CreateReplacementImp(repository);
	}
	
	@Bean
	public NegateReplacement negateReplacement (ReplacementRepository repository) {
		return new NegateReplacementImp(repository);
	}
	
	@Bean
	public AcceptReplacement acceptReplacement (ReplacementRepository repository, ValueUnitProductService service, EventReplacementPublisher publisher) {
		return new AcceptReplacementImp(repository, service, publisher);
	}
	
	@Bean
	public ListReplacement listReplacement (ReplacementRepository repository) {
		return new ListReplacementImpl(repository);
	}
}
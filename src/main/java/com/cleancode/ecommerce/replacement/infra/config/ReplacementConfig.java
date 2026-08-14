package com.cleancode.ecommerce.replacement.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cleancode.ecommerce.replacement.application.usecase.AcceptReplacement;
import com.cleancode.ecommerce.replacement.application.usecase.AcceptReplacementImp;
import com.cleancode.ecommerce.replacement.application.usecase.CreateReplacement;
import com.cleancode.ecommerce.replacement.application.usecase.CreateReplacementImp;
import com.cleancode.ecommerce.replacement.application.usecase.ListReplacement;
import com.cleancode.ecommerce.replacement.application.usecase.ListReplacementImpl;
import com.cleancode.ecommerce.replacement.application.usecase.NegateReplacement;
import com.cleancode.ecommerce.replacement.application.usecase.NegateReplacementImp;
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
	public AcceptReplacement acceptReplacement (ReplacementRepository repository) {
		return new AcceptReplacementImp(repository);
	}
	
	@Bean
	public ListReplacement listReplacement (ReplacementRepository repository) {
		return new ListReplacementImpl(repository);
	}
}
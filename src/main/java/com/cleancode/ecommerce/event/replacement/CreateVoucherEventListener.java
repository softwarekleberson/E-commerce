package com.cleancode.ecommerce.event.replacement;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.cleancode.ecommerce.promotional.usecase.contract.ReplacementVoucher;

@Component
public class CreateVoucherEventListener {

	private final ReplacementVoucher replacementVoucher;

	public CreateVoucherEventListener(ReplacementVoucher replacementVoucher) {
		this.replacementVoucher = replacementVoucher;
	}
	
	@Async
	@EventListener
	public void onSeparationItem(ReplacementEvent event) {
		replacementVoucher.execute(event.customerId(), event.value());
	}
}
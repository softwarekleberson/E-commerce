package com.cleancode.ecommerce.cart.application.service;

import org.springframework.scheduling.annotation.Scheduled;

import com.cleancode.ecommerce.cart.application.service.contract.ExpireCartReservations;

public class CartReservationExpirationScheduler {

	private final ExpireCartReservations expireCartReservations;

	public CartReservationExpirationScheduler(ExpireCartReservations expireCartReservations) {
		this.expireCartReservations = expireCartReservations;
	}

	@Scheduled(fixedDelay = 30_000)
	public void expireReservations() {
		expireCartReservations.execute();
	}
}

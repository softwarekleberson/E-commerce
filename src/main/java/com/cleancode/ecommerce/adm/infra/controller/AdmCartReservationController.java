package com.cleancode.ecommerce.adm.infra.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cleancode.ecommerce.cart.application.dtos.input.CartReservationTimeoutDto;
import com.cleancode.ecommerce.cart.application.service.contract.CartReservationTimeout;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/adm/cart-reservation-timeout")
@CrossOrigin(origins = "*")
public class AdmCartReservationController {

	private final CartReservationTimeout reservationTimeout;

	public AdmCartReservationController(CartReservationTimeout reservationTimeout) {
		this.reservationTimeout = reservationTimeout;
	}

	@GetMapping
	public ResponseEntity<CartReservationTimeoutDto> getTimeout() {
		return ResponseEntity.ok(new CartReservationTimeoutDto(reservationTimeout.getMaxDurationMinutes()));
	}

	@PutMapping
	public ResponseEntity<CartReservationTimeoutDto> updateTimeout(
			@Valid @RequestBody CartReservationTimeoutDto dto) {
		reservationTimeout.updateMaxDurationMinutes(dto.minutes());
		return ResponseEntity.ok(dto);
	}
}

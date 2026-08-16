package com.cleancode.ecommerce.adm.infra.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cleancode.ecommerce.promotional.usecase.contract.CreateVoucher;
import com.cleancode.ecommerce.promotional.usecase.dto.CreateVoucherDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/adm")
@CrossOrigin(origins = "*")
public class AdmVoucherController {

	private final CreateVoucher createVoucher;

	public AdmVoucherController(CreateVoucher createVoucher) {
		this.createVoucher = createVoucher;
	}

	@PostMapping("/voucher")
	public ResponseEntity<Void> createVoucher(@Valid @RequestBody CreateVoucherDto dto) {
		createVoucher.execute(dto);
		return ResponseEntity.noContent().build();
	}
}

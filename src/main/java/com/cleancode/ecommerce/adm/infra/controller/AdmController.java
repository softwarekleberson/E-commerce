package com.cleancode.ecommerce.adm.infra.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cleancode.ecommerce.customer.application.dtos.customer.ChangeStatusCustomerDto;
import com.cleancode.ecommerce.customer.application.dtos.customer.ListAllCustomersDto;
import com.cleancode.ecommerce.customer.application.usecase.contract.ChangeActivationStatusAdm;
import com.cleancode.ecommerce.customer.application.usecase.contract.ListAllCustomers;
import com.cleancode.ecommerce.promotional.usecase.contract.CreateVoucher;
import com.cleancode.ecommerce.promotional.usecase.dto.CreateVoucherDto;
import com.cleancode.ecommerce.shared.util.pagination.controller.PageResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/adm")
@CrossOrigin(origins = "*")
public class AdmController {

	private final ListAllCustomers listAllCustomers;
	private final ChangeActivationStatusAdm changeActivationStatusAdm;
	private final CreateVoucher createVoucher;

	public AdmController(ListAllCustomers listAllCustomers, ChangeActivationStatusAdm changeActivationStatusAdm,
			CreateVoucher createVoucher) {

		this.listAllCustomers = listAllCustomers;
		this.changeActivationStatusAdm = changeActivationStatusAdm;
		this.createVoucher = createVoucher;
	}

	@PostMapping("/voucher")
	public ResponseEntity<Void> createVoucher(@Valid @RequestBody CreateVoucherDto dto) {
		createVoucher.execute(dto);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<PageResponse<ListAllCustomersDto>> listAllCustomers(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

		Pageable pageable = PageRequest.of(page, size);
		var result = listAllCustomers.execute(pageable);
		return ResponseEntity.ok(PageResponse.from(result));
	}

	@PutMapping("/change-status")
	public ResponseEntity<Void> changeCustomerActivationStatus(@Valid @RequestBody ChangeStatusCustomerDto dto) {
		changeActivationStatusAdm.execute(dto.customerId());
		return ResponseEntity.noContent().build();
	}
}

package com.cleancode.ecommerce.customer.infra.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cleancode.ecommerce.customer.application.dtos.address.CreateChargeDto;
import com.cleancode.ecommerce.customer.application.dtos.address.CreateDeliveryDto;
import com.cleancode.ecommerce.customer.application.dtos.address.UpdateAddressDto;
import com.cleancode.ecommerce.customer.application.dtos.card.CreateCardDto;
import com.cleancode.ecommerce.customer.application.dtos.customer.ListCustomerDto;
import com.cleancode.ecommerce.customer.application.dtos.customer.UpdateCustomerDto;
import com.cleancode.ecommerce.customer.application.dtos.customer.UpdatePasswordDto;
import com.cleancode.ecommerce.customer.application.usecase.contract.CreateCustomerCard;
import com.cleancode.ecommerce.customer.application.usecase.contract.CreateCustomerCharge;
import com.cleancode.ecommerce.customer.application.usecase.contract.CreateCustomerDelivery;
import com.cleancode.ecommerce.customer.application.usecase.contract.DeleteCharge;
import com.cleancode.ecommerce.customer.application.usecase.contract.DeleteDelivery;
import com.cleancode.ecommerce.customer.application.usecase.contract.ListCustomer;
import com.cleancode.ecommerce.customer.application.usecase.contract.UpdateCharge;
import com.cleancode.ecommerce.customer.application.usecase.contract.UpdateCustomer;
import com.cleancode.ecommerce.customer.application.usecase.contract.UpdateDelivery;
import com.cleancode.ecommerce.customer.application.usecase.contract.UpdatePassword;
import com.cleancode.ecommerce.promotional.usecase.contract.ListVoucherCustomer;
import com.cleancode.ecommerce.promotional.usecase.dto.ListVoucherDto;
import com.cleancode.ecommerce.shared.util.pagination.controller.PageResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "*")
public class CustomerController {

	private final CreateCustomerDelivery createCustomerDelivery;
	private final CreateCustomerCharge createCustomerCharge;
	private final ListCustomer listCustomer;
	private final UpdateCustomer updateCustomer;
	private final UpdatePassword updatePassword;
	private final DeleteCharge deleteCharge;
	private final DeleteDelivery deleteDelivery;
	private final UpdateCharge updateCharge;
	private final UpdateDelivery updateDelivery;
	private final CreateCustomerCard createCard;
	private final ListVoucherCustomer listVoucherCustomer;

	public CustomerController(CreateCustomerDelivery createCustomerDelivery, CreateCustomerCharge createCustomerCharge,
			ListCustomer listCustomer, UpdateCustomer updateCustomer, UpdatePassword updatePassword,
			DeleteCharge deleteCharge, DeleteDelivery deleteDelivery, UpdateCharge updateCharge,
			UpdateDelivery updateDelivery, CreateCustomerCard createCard, ListVoucherCustomer listVoucherCustomer) {

		this.createCustomerDelivery = createCustomerDelivery;
		this.createCustomerCharge = createCustomerCharge;
		this.listCustomer = listCustomer;
		this.updateCustomer = updateCustomer;
		this.updatePassword = updatePassword;
		this.deleteCharge = deleteCharge;
		this.deleteDelivery = deleteDelivery;
		this.updateCharge = updateCharge;
		this.updateDelivery = updateDelivery;
		this.createCard = createCard;
		this.listVoucherCustomer = listVoucherCustomer;
	}

	// ----------------------
	// Customers
	// ----------------------

	@GetMapping("/me")
	public ResponseEntity<ListCustomerDto> getCurrentCustomer(Authentication authentication) {
		if (authentication == null || !authentication.isAuthenticated()
				|| authentication instanceof AnonymousAuthenticationToken) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		String email = authentication.getName();
		ListCustomerDto customer = listCustomer.execute(email);
		return ResponseEntity.ok(customer);
	}

	@GetMapping("/voucher")
	public ResponseEntity<PageResponse<ListVoucherDto>> getAllVoucherCustomer(Authentication authentication,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

		if (authentication == null || !authentication.isAuthenticated()
				|| authentication instanceof AnonymousAuthenticationToken) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		String email = authentication.getName();
		
		Pageable pageable = PageRequest.of(page, size);
		var result = listVoucherCustomer.execute(email, pageable);
		return ResponseEntity.ok(PageResponse.from(result));
	}

	@PutMapping("/me")
	public ResponseEntity<ListCustomerDto> updateCustomer(Authentication authentication,
			@Valid @RequestBody UpdateCustomerDto dto) {
		if (authentication == null || !authentication.isAuthenticated()
				|| authentication instanceof AnonymousAuthenticationToken) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		String email = authentication.getName();
		return ResponseEntity.ok(updateCustomer.execute(email, dto));
	}

	@PutMapping("/password")
	public ResponseEntity<Void> updatePassword(Authentication authentication,
			@Valid @RequestBody UpdatePasswordDto dto) {
		if (authentication == null || !authentication.isAuthenticated()
				|| authentication instanceof AnonymousAuthenticationToken) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		String email = authentication.getName();
		updatePassword.execute(email, dto);
		return ResponseEntity.noContent().build();
	}

	// ----------------------
	// Deliveries
	// ----------------------

	@PostMapping("/delivery")
	public ResponseEntity<ListCustomerDto> addDelivery(Authentication authentication,
			@Valid @RequestBody CreateDeliveryDto dto) {

		if (authentication == null || !authentication.isAuthenticated()
				|| authentication instanceof AnonymousAuthenticationToken) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		String email = authentication.getName();
		var updatedCustomer = createCustomerDelivery.execute(email, dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(updatedCustomer);
	}

	@PutMapping("/delivery/{deliveryId}")
	public ResponseEntity<ListCustomerDto> updateDelivery(Authentication authentication,
			@PathVariable String deliveryId, @Valid @RequestBody UpdateAddressDto dto) {

		if (authentication == null || !authentication.isAuthenticated()
				|| authentication instanceof AnonymousAuthenticationToken) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		String email = authentication.getName();

		var updateCustomer = updateDelivery.execute(email, deliveryId, dto);
		return ResponseEntity.ok(updateCustomer);
	}

	@DeleteMapping("/delivery/{deliveryId}")
	public ResponseEntity<Void> deleteDelivery(Authentication authentication, @PathVariable String deliveryId) {

		if (authentication == null || !authentication.isAuthenticated()
				|| authentication instanceof AnonymousAuthenticationToken) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		String email = authentication.getName();

		deleteDelivery.execute(email, deliveryId);
		return ResponseEntity.noContent().build();
	}

	// ----------------------
	// Charges
	// ----------------------

	@PostMapping("/charge")
	public ResponseEntity<ListCustomerDto> addCharge(Authentication authentication,
			@Valid @RequestBody CreateChargeDto dto) {

		if (authentication == null || !authentication.isAuthenticated()
				|| authentication instanceof AnonymousAuthenticationToken) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		String email = authentication.getName();

		var updatedCustomer = createCustomerCharge.execute(email, dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(updatedCustomer);
	}

	@PutMapping("/charge/{chargeId}")
	public ResponseEntity<ListCustomerDto> updateCharge(Authentication authentication, @PathVariable String chargeId,
			@Valid @RequestBody UpdateAddressDto dto) {

		if (authentication == null || !authentication.isAuthenticated()
				|| authentication instanceof AnonymousAuthenticationToken) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		String email = authentication.getName();

		var updateCustomer = updateCharge.execute(email, chargeId, dto);
		return ResponseEntity.ok(updateCustomer);
	}

	@DeleteMapping("/charge/{chargeId}")
	public ResponseEntity<Void> deleteCharge(Authentication authentication, @PathVariable String chargeId) {

		if (authentication == null || !authentication.isAuthenticated()
				|| authentication instanceof AnonymousAuthenticationToken) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		String email = authentication.getName();

		deleteCharge.execute(email, chargeId);
		return ResponseEntity.noContent().build();
	}

	// ----------------------
	// Cards
	// ----------------------

	@PostMapping("/card")
	public ResponseEntity<ListCustomerDto> addCard(Authentication authentication,
			@Valid @RequestBody CreateCardDto dto) {

		if (authentication == null || !authentication.isAuthenticated()
				|| authentication instanceof AnonymousAuthenticationToken) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		String email = authentication.getName();

		var createNewCard = createCard.execute(email, dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(createNewCard);
	}
}
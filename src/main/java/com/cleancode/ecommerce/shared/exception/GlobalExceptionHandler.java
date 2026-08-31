package com.cleancode.ecommerce.shared.exception;

import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.cleancode.ecommerce.cart.domain.exception.IllegalCartException;
import com.cleancode.ecommerce.customer.domain.card.exception.IllegalCardException;
import com.cleancode.ecommerce.customer.domain.customer.exception.IllegalContactException;
import com.cleancode.ecommerce.order.domain.exceptions.IllegalDomainOrder;
import com.cleancode.ecommerce.payment.domain.exceptions.IllegalDomainPayment;
import com.cleancode.ecommerce.product.domain.exception.IllegalDimensionException;
import com.cleancode.ecommerce.product.domain.exception.IllegalPricingException;
import com.cleancode.ecommerce.promotional.domain.exception.IllegalVoucherException;
import com.cleancode.ecommerce.replacement.domain.exception.IllegalReplacementException;
import com.cleancode.ecommerce.stock.domain.exception.IllegalReservationException;
import com.cleancode.ecommerce.stock.domain.exception.IllegalStockException;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(IllegalDomainException.class)
	public ResponseEntity<DetailsError> handleExceptionPersonalized(IllegalDomainException ex) {
		DetailsError error = new DetailsError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Custom exception customer",
				ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(IllegalDomainPayment.class)
	public ResponseEntity<DetailsError> handleExceptionPersonalized(IllegalDomainPayment ex) {
		DetailsError error = new DetailsError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Custom exception customer",
				ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(IllegalCpfException.class)
	public ResponseEntity<DetailsError> handleExceptionPersonalized(IllegalCpfException ex) {
		DetailsError error = new DetailsError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Custom exception customer",
				ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(IllegalContactException.class)
	public ResponseEntity<DetailsError> handleExceptionPersonalized(IllegalContactException ex) {
		DetailsError error = new DetailsError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Custom exception customer",
				ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(IllegalPricingException.class)
	public ResponseEntity<DetailsError> handleExceptionPersonalized(IllegalPricingException ex) {
		DetailsError error = new DetailsError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Custom exception product",
				ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(IllegalDimensionException.class)
	public ResponseEntity<DetailsError> handleExceptionPersonalized(IllegalDimensionException ex) {
		DetailsError error = new DetailsError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Custom exception product product",
				ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(IllegalCartException.class)
	public ResponseEntity<DetailsError> handleExceptionPersonalized(IllegalCartException ex) {
		DetailsError error = new DetailsError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Custom exception product cart",
				ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(IllegalReservationException.class)
	public ResponseEntity<DetailsError> handleExceptionPersonalized(IllegalReservationException ex) {
		DetailsError error = new DetailsError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Custom exception product stock",
				ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(IllegalStockException.class)
	public ResponseEntity<DetailsError> handleExceptionPersonalized(IllegalStockException ex) {
		DetailsError error = new DetailsError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Custom exception stock",
				ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(IllegalCardException.class)
	public ResponseEntity<DetailsError> handleExceptionPersonalized(IllegalCardException ex) {
		DetailsError error = new DetailsError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Custom exception card",
				ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(IllegalReplacementException.class)
	public ResponseEntity<DetailsError> handleExceptionPersonalized(IllegalReplacementException ex) {
		DetailsError error = new DetailsError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Custom exception replacement",
				ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(IllegalVoucherException.class)
	public ResponseEntity<DetailsError> handleExceptionPersonalized(IllegalVoucherException ex) {
		DetailsError error = new DetailsError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Custom exception voucher",
				ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(IllegalDomainOrder.class)
	public ResponseEntity<DetailsError> handleExceptionPersonalized(IllegalDomainOrder ex) {
		DetailsError error = new DetailsError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Custom exception order",
				ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<DetailsError> handleExceptionPersonalized(CustomerNotFoundException ex) {
		DetailsError error = new DetailsError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Custom exception customer",
				ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	// Invalid argument error (e.g. @Valid)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<DetailsError> handleValidationException(MethodArgumentNotValidException ex) {
		String message = ex.getBindingResult().getFieldErrors().stream()
				.map(e -> e.getField() + ": " + e.getDefaultMessage()).findFirst().orElse("Validation Error");

		DetailsError erro = new DetailsError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Validation Error",
				message);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}

	// Poorly formatted request body (invalid JSON, for example)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<DetailsError> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
		DetailsError erro = new DetailsError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
				"Poorly formatted request", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}

	// Invalid route argument (e.g. invalid ID in URL)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<DetailsError> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
		DetailsError erro = new DetailsError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Invalid parameter",
				"The Value '" + ex.getValue() + "' is not valid for the parameter '" + ex.getName() + "'.");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}

	// Any other error (500)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<DetailsError> handleGenericException(Exception ex) {
		DetailsError erro = new DetailsError(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"Internal server error", ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<DetailsError> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
		String mensagem = "Data integrity breach.";

		Throwable causa = ex.getCause();
		if (causa instanceof ConstraintViolationException) {
			ConstraintViolationException constraintEx = (ConstraintViolationException) causa;
			if (constraintEx.getConstraintViolations() != null && constraintEx.getConstraintViolations().contains("customer.email")) {
				mensagem = "There is already a customer registered with this Email.";
			}
		}

		DetailsError erro = new DetailsError(LocalDateTime.now(), HttpStatus.CONFLICT.value(), "Data conflict",
				mensagem);
		return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
	}
}

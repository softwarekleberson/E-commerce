package com.cleancode.ecommerce.payment.infra.controller;

import org.springframework.security.core.Authentication; 
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cleancode.ecommerce.payment.application.dto.PaymentDetails;
import com.cleancode.ecommerce.payment.application.service.payment.contract.Checkout;

import jakarta.validation.Valid;

@RestController
@RequestMapping("payment")
@CrossOrigin(origins = "*")
public class PaymentController {

	private final Checkout checkout;

    public PaymentController(Checkout checkout) {
        this.checkout = checkout;
    }

    @PostMapping("/checkout")
    public ResponseEntity<Void> processCheckout(
            Authentication authentication, 
            @Valid @RequestBody PaymentDetails dto) {
        
        String email = authentication.getName();
   
        checkout.execute(email, dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

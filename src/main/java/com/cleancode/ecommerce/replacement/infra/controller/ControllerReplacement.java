package com.cleancode.ecommerce.replacement.infra.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cleancode.ecommerce.replacement.application.dto.CreateReplacementDto;
import com.cleancode.ecommerce.replacement.application.usecase.CreateReplacement;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customer/replacement")
@CrossOrigin(origins = "*")
public class ControllerReplacement {

    private final CreateReplacement createReplacement;
    
    public ControllerReplacement(CreateReplacement createReplacement) {
        this.createReplacement = createReplacement;
    }
    
    @PostMapping
    public ResponseEntity<Void> createReplacement(
            @Valid @RequestBody CreateReplacementDto dto,
            Authentication authentication) {
        
        if (authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        createReplacement.execute(dto);
        return ResponseEntity.noContent().build();
    }
}
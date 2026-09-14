package com.cleancode.ecommerce.adm.infra.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cleancode.ecommerce.adm.application.CreateNewAdm;
import com.cleancode.ecommerce.adm.application.dto.CreateAdmDto;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/adm")
@CrossOrigin(origins = "*")
public class AdmCreateAdm {

	private final CreateNewAdm createNewAdm;

	public AdmCreateAdm(CreateNewAdm createNewAdm) {
		this.createNewAdm = createNewAdm;
	}

	@Operation(summary = "Create new Adm.", security = {})
	@PostMapping("/create")
	public ResponseEntity<Void> createAdm(@Valid @RequestBody CreateAdmDto dto) {
		createNewAdm.execute(dto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
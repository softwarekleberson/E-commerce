package com.cleancode.ecommerce.adm.infra.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cleancode.ecommerce.replacement.application.dto.ListReplacementOpenDto;
import com.cleancode.ecommerce.replacement.application.usecase.AcceptReplacement;
import com.cleancode.ecommerce.replacement.application.usecase.ListReplacement;
import com.cleancode.ecommerce.replacement.application.usecase.NegateReplacement;
import com.cleancode.ecommerce.shared.util.pagination.controller.PageResponse;

@RestController
@RequestMapping("/adm/replacement")
@CrossOrigin(origins = "*")
public class AdmReplacement {

	private final AcceptReplacement acceptReplacement;
	private final NegateReplacement negateReplacement;
	private final ListReplacement listReplacement;
	
	public AdmReplacement(AcceptReplacement acceptReplacement, NegateReplacement negateReplacement, ListReplacement listReplacement) {
		this.acceptReplacement = acceptReplacement;
		this.negateReplacement = negateReplacement;
		this.listReplacement = listReplacement;
	}
	
	@PutMapping("/{reservationId}/accept")
	public ResponseEntity<Void> acceptReplacement(Authentication authentication, @PathVariable String reservationId){
		if (authentication == null || !authentication.isAuthenticated()
				|| authentication instanceof AnonymousAuthenticationToken) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		acceptReplacement.execute(reservationId);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{reservationId}/negate")
	public ResponseEntity<Void> negateReplacement(Authentication authentication, @PathVariable String reservationId){
		if (authentication == null || !authentication.isAuthenticated()
				|| authentication instanceof AnonymousAuthenticationToken) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		negateReplacement.execute(reservationId);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public ResponseEntity<PageResponse<ListReplacementOpenDto>> listReplacementOpen(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
		Pageable pageable = PageRequest.of(page, size);
		var result = listReplacement.execute(pageable);
		return ResponseEntity.ok(PageResponse.from(result));
	}
}
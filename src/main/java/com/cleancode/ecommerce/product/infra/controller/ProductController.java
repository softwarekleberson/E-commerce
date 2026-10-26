package com.cleancode.ecommerce.product.infra.controller;

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

import com.cleancode.ecommerce.product.application.dto.input.CreateProductDto;
import com.cleancode.ecommerce.product.application.dto.input.ModifySellingPriceDto;
import com.cleancode.ecommerce.product.application.dto.input.ProductStatusChangeDto;
import com.cleancode.ecommerce.product.application.dto.input.ReviseDetailsDto;
import com.cleancode.ecommerce.product.application.dto.output.ListProductDto;
import com.cleancode.ecommerce.product.infra.service.cache.ProductCacheService;
import com.cleancode.ecommerce.shared.util.pagination.controller.PageResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("adm/product")
@CrossOrigin(origins = "*")
public class ProductController {

	private final ProductCacheService productCacheService;
	
	public ProductController(ProductCacheService productCacheService) {
		this.productCacheService = productCacheService;
	}

	@PostMapping
	public ResponseEntity<Void> createProduct(@Valid @RequestBody CreateProductDto dto) {
		productCacheService.create(dto);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/inactive")
	public PageResponse<ListProductDto> getAllProductsInactive(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size) {

	    return productCacheService.getInactiveProducts(page, size);
	}

	@PutMapping("/{productId}")
	public ResponseEntity<Void> reviseDetailsProduct(@Valid @RequestBody ReviseDetailsDto dto,
			@PathVariable String productId) {
		productCacheService.revise(productId, dto);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/activate")
	public ResponseEntity<Void> activateProduct(@Valid @RequestBody ProductStatusChangeDto dto) {
		productCacheService.manuallyActivate(dto);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/selling/price")
	public ResponseEntity<Void> priceAboveProfitMargin(@Valid @RequestBody ModifySellingPriceDto dto) {
		productCacheService.changePrice(dto);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/disable")
	public ResponseEntity<Void> deactivateProduct(@Valid @RequestBody ProductStatusChangeDto dto) {
		productCacheService.manuallyDeactivate(dto);
		return ResponseEntity.noContent().build();
	}
}

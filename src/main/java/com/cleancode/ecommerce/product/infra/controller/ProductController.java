package com.cleancode.ecommerce.product.infra.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.cleancode.ecommerce.product.application.usecase.contract.CreateProduct;
import com.cleancode.ecommerce.product.application.usecase.contract.IncreaseSellingPriceAboveProfitMargin;
import com.cleancode.ecommerce.product.application.usecase.contract.ListActiveProduct;
import com.cleancode.ecommerce.product.application.usecase.contract.ListAllProductActive;
import com.cleancode.ecommerce.product.application.usecase.contract.ListAllProductsInactive;
import com.cleancode.ecommerce.product.application.usecase.contract.ManualProductActivation;
import com.cleancode.ecommerce.product.application.usecase.contract.ManualProductDeactivation;
import com.cleancode.ecommerce.product.application.usecase.contract.ReviseDetails;
import com.cleancode.ecommerce.shared.util.pagination.controller.PageResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("adm/product")
@CrossOrigin(origins = "*")
public class ProductController {

	private final CreateProduct createProduct;
	private final ReviseDetails reviseDetails;
	private final ManualProductDeactivation manualProductDeactivation;
	private final ManualProductActivation manualProductActivation;
	private final IncreaseSellingPriceAboveProfitMargin increaseSellingPriceAboveProfitMargin;
	private final ListAllProductsInactive listAllProductsInactive;
	
	public ProductController(CreateProduct createProduct, ListAllProductActive listAllProduct, ListActiveProduct listProduct,
			ReviseDetails reviseDetails, ManualProductDeactivation manualProductDeactivation,
			ManualProductActivation manualProductActivation,
			IncreaseSellingPriceAboveProfitMargin increaseSellingPriceAboveProfitMargin,
			ListAllProductsInactive listAllProductsInactive) {
		
		this.createProduct = createProduct;
		this.reviseDetails = reviseDetails;
		this.manualProductDeactivation = manualProductDeactivation;
		this.manualProductActivation = manualProductActivation;
		this.increaseSellingPriceAboveProfitMargin = increaseSellingPriceAboveProfitMargin;
		this.listAllProductsInactive = listAllProductsInactive;
	}

	@PostMapping
	public ResponseEntity<Void> createProduct(@Valid @RequestBody CreateProductDto dto) {
		createProduct.execute(dto);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/inactive")
	public ResponseEntity<PageResponse<ListProductDto>> getAllProductsInactive(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size) {

	    Pageable pageable = PageRequest.of(page, size);
	    var result = listAllProductsInactive.execute(pageable);
	    return ResponseEntity.ok(PageResponse.from(result));
	}

	@PutMapping("/{productId}")
	public ResponseEntity<Void> reviseDetailsProduct(@Valid @RequestBody ReviseDetailsDto dto,
			@PathVariable String productId) {
		reviseDetails.execute(productId, dto);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/activate")
	public ResponseEntity<Void> activateProduct(@Valid @RequestBody ProductStatusChangeDto dto) {
		manualProductActivation.execute(dto);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/selling/price")
	public ResponseEntity<Void> priceAboveProfitMargin(@Valid @RequestBody ModifySellingPriceDto dto) {
		increaseSellingPriceAboveProfitMargin.execute(dto);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/disable")
	public ResponseEntity<Void> deactivateProduct(@Valid @RequestBody ProductStatusChangeDto dto) {
		manualProductDeactivation.execute(dto);
		return ResponseEntity.noContent().build();
	}
}
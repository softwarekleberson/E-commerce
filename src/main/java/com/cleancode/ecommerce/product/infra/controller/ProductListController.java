package com.cleancode.ecommerce.product.infra.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cleancode.ecommerce.product.application.dto.output.ListProductDto;
import com.cleancode.ecommerce.product.infra.service.cache.ProductCacheService;
import com.cleancode.ecommerce.shared.util.pagination.controller.PageResponse;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("public/product")
@CrossOrigin(origins = "*")
public class ProductListController {

    private final ProductCacheService productCacheService;

    public ProductListController(ProductCacheService productCacheService) {
        this.productCacheService = productCacheService;
    }

    @Operation(
    		summary = "Get product by id",
    		security = {}
    )
    @GetMapping("/{id}")
    public ListProductDto getProductById(@PathVariable String id) {
        return productCacheService.getActiveProductById(id);
    }

    @Operation(
		    summary = "Get all product",
		    security = {}
	)
    @GetMapping
    public PageResponse<ListProductDto> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {

        return productCacheService.getActiveProducts(page, size);
    }
}

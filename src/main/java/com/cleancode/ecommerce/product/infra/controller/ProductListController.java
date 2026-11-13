package com.cleancode.ecommerce.product.infra.controller;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cleancode.ecommerce.product.application.dto.output.ListProductDto;
import com.cleancode.ecommerce.product.application.usecase.contract.SearchProducts;
import com.cleancode.ecommerce.product.domain.ProductCategory;
import com.cleancode.ecommerce.product.domain.ProductSearchCriteria;
import com.cleancode.ecommerce.product.infra.service.cache.ProductCacheService;
import com.cleancode.ecommerce.shared.kernel.TypeCoin;
import com.cleancode.ecommerce.shared.util.pagination.controller.PageRequests;
import com.cleancode.ecommerce.shared.util.pagination.controller.PageResponse;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("public/product")
@CrossOrigin(origins = "*")
public class ProductListController {

    private final ProductCacheService productCacheService;
    private final SearchProducts searchProducts;

    public ProductListController(ProductCacheService productCacheService, SearchProducts searchProducts) {
        this.productCacheService = productCacheService;
        this.searchProducts = searchProducts;
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

    @Operation(summary = "Search public products", security = {})
    @GetMapping("/search")
    public PageResponse<ListProductDto> searchProducts(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) BigDecimal minimumPrice,
            @RequestParam(required = false) BigDecimal maximumPrice,
            @RequestParam(required = false) TypeCoin typeCoin,
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String mediaId,
            @RequestParam(required = false) String mediaUrl,
            @RequestParam(required = false) String mediaDescription,
            @RequestParam(required = false) BigDecimal minimumPricing,
            @RequestParam(required = false) BigDecimal maximumPricing,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        ProductSearchCriteria criteria = new ProductSearchCriteria(
                id, name, description, minimumPrice, maximumPrice, typeCoin,
                category, brand, mediaId, mediaUrl, mediaDescription, minimumPricing, maximumPricing);
        return PageResponse.from(searchProducts.execute(criteria, PageRequests.of(page, size)));
    }
}

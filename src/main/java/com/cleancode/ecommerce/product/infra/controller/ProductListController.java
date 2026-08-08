package com.cleancode.ecommerce.product.infra.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cleancode.ecommerce.product.application.dto.output.ListProductDto;
import com.cleancode.ecommerce.product.application.usecase.contract.ListActiveProduct;
import com.cleancode.ecommerce.product.application.usecase.contract.ListAllProductActive;
import com.cleancode.ecommerce.shared.util.pagination.controller.PageResponse;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("public/product")
@CrossOrigin(origins = "*")
public class ProductListController {


    private final ListAllProductActive listAllProduct;
    private final ListActiveProduct listProduct;

    public ProductListController(ListAllProductActive listAllProduct, ListActiveProduct listProduct) {
        this.listAllProduct = listAllProduct;
        this.listProduct = listProduct;
    }

    @Operation(summary = "Get product by id", security = {})
    @GetMapping("/{id}")
    public ListProductDto getProductById(@PathVariable String id) {
        return listProduct.execute(id);
    }

    @Operation(summary = "Get all products", security = {})
    @Cacheable(value = "list-all-products", key = "{#page, #size}")
    @GetMapping
    public PageResponse<ListProductDto> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {

        Pageable pageable = PageRequest.of(page, size);
        var result = listAllProduct.execute(pageable);

        return PageResponse.from(result);
    }
}
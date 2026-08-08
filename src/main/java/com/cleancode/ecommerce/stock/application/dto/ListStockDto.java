package com.cleancode.ecommerce.stock.application.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.cleancode.ecommerce.stock.domain.Stock;

public record ListStockDto(
        String id,
        String productId,
        int totalQuantity,
        int quantityAvailable,
        List<ListInputProductDto> productInputs
) {
	
    public ListStockDto(Stock stock) {
        this(
            stock.getStockId().getStockId(),
            stock.getProductId().getProductId(),
            stock.getTotalQuantity(),
            stock.getQuantityAvailable(),
            stock.getProductInput() == null ? List.of() :
            stock.getProductInput().stream()
            .map(ListInputProductDto::new)
            .collect(Collectors.toList())
        );
    }
}
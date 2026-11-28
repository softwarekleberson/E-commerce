package com.cleancode.ecommerce.product.application.dto.output;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateProductOutputDto(@JsonProperty("product_id") String productId) {
}

package com.cleancode.ecommerce.product.application.dto.input;

import com.cleancode.ecommerce.product.domain.ProductStatusCategory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductStatusChangeDto(@NotBlank String productId, @NotBlank String justification, @NotNull ProductStatusCategory category) {

}

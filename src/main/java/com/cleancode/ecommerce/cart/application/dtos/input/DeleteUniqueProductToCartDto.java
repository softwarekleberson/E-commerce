package com.cleancode.ecommerce.cart.application.dtos.input;

import jakarta.validation.constraints.NotBlank;

public record DeleteUniqueProductToCartDto(@NotBlank String reservationId) {

}

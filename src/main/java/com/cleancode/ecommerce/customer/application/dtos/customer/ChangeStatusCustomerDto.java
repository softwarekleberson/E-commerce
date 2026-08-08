package com.cleancode.ecommerce.customer.application.dtos.customer;

import jakarta.validation.constraints.NotNull;

public record ChangeStatusCustomerDto(@NotNull String customerId) {

}

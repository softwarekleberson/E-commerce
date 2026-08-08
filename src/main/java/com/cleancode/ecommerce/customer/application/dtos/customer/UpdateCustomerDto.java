package com.cleancode.ecommerce.customer.application.dtos.customer;

import java.time.LocalDate;

import com.cleancode.ecommerce.customer.domain.contact.TypePhone;

public record UpdateCustomerDto(String name, LocalDate birth, String ddd, String phone, TypePhone typePhone) {
}

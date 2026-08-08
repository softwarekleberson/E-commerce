package com.cleancode.ecommerce.customer.application.dtos.address;

public record UpdateAddressDto(String receiver, Boolean main, String street, String number, String neighborhood,
		String zipCode, String observation, String streetType, String typeResidence, String city, String state,
		String country, String deliveryPhrase) {
}

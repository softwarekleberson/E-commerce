package com.cleancode.ecommerce.customer.application.dtos.address;

import java.util.UUID;

import com.cleancode.ecommerce.customer.domain.address.Delivery;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateDeliveryDto {

	private String id;

	@NotNull
	private Boolean main;

	@NotBlank
	private String receiver;

	@NotBlank
	private String street;

	@NotBlank
	private String number;

	@NotBlank
	private String neighborhood;

	@NotBlank(message = "The Zip Code needs this format : xxxxxxx ")
	private String zipCode;

	@Size(max = 255)
	private String observation;

	@NotBlank
	private String streetType;

	@NotBlank
	private String typeResidence;

	@NotBlank
	private String city;

	@NotBlank
	private String state;

	@NotBlank
	private String country;

	@NotBlank
	@Size(max = 255)
	private String deliveryPhrase;

	public CreateDeliveryDto(String receiver, Boolean main, String street, String number, String neighborhood,
			String zipCode, String observation, String streetType, String typeResidence, String city, String state,
			String country, String deliveryPhrase) {

		this.id = UUID.randomUUID().toString();
		this.main = main;
		this.receiver = receiver;
		this.street = street;
		this.number = number;
		this.neighborhood = neighborhood;
		this.zipCode = zipCode;
		this.observation = observation;
		this.streetType = streetType;
		this.typeResidence = typeResidence;
		this.city = city;
		this.state = state;
		this.country = country;
		this.deliveryPhrase = deliveryPhrase;
	}

	public String getId() {
		return id;
	}

	public Boolean getMain() {
		return main;
	}

	public String getReceiver() {
		return receiver;
	}

	public String getStreet() {
		return street;
	}

	public String getNumber() {
		return number;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getObservation() {
		return observation;
	}

	public String getStreetType() {
		return streetType;
	}

	public String getTypeResidence() {
		return typeResidence;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getCountry() {
		return country;
	}

	public String getDeliveryPhrase() {
		return deliveryPhrase;
	}

	public Delivery createDelivery() {
		return new Delivery(id, main, deliveryPhrase, receiver, street, number, neighborhood, zipCode, observation,
				streetType, typeResidence, city, state, country);
	}
}

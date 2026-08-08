package com.cleancode.ecommerce.customer.domain.address;

import com.cleancode.ecommerce.shared.exception.IllegalDomainException;

public class Delivery extends Address{

	private String deliveryPhrase; 
	
	public Delivery(String id, Boolean main, String deliveryPhrase, String receiver, String street, String number, String neighborhood, String zipCode,
			String observation, String streetType, String typeResidence, String city, String state, String country) {
		super(id, main, receiver, street, number, neighborhood, zipCode, observation, streetType, typeResidence, city, state, country);
		
		if(isValid(deliveryPhrase)) throw new IllegalDomainException("Delivery Phrase is requerid");
		this.deliveryPhrase = deliveryPhrase;
	}
	
	public void update(String receiver, Boolean main, String street, String number, String neighborhood, String zipCode,
			String observation, String streetType, String typeResidence, String city, String state, String country, String deliveryPhrase) {

		if (receiver != null && !receiver.isBlank())
			this.receiver = receiver;
		if (main != null)
			this.main = main;
		if (street != null && !street.isBlank())
			this.street = street;
		if (number != null && !number.isBlank())
			this.number = number;
		if (neighborhood != null && !neighborhood.isBlank())
			this.neighborhood = neighborhood;
		if (zipCode != null && !zipCode.isBlank() && isZipCode(zipCode))
			this.zipCode = zipCode;
		if (observation != null && !observation.isBlank())
			this.observation = observation;
		if (streetType != null && !streetType.isBlank())
			this.streetType = streetType;
		if (typeResidence != null && !typeResidence.isBlank())
			this.residenceType = typeResidence;
		if (city != null && !city.isBlank())
			this.city = city;
		if (state != null && !state.isBlank())
			this.state = state;
		if (country != null && !country.isBlank())
			this.country = country;
		if (deliveryPhrase != null && !deliveryPhrase.isBlank())
			this.deliveryPhrase = deliveryPhrase;
	}

	public String getDeliveryPhrase() {
		return deliveryPhrase;
	}
}
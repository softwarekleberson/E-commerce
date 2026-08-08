package com.cleancode.ecommerce.customer.infra.mapper;

import com.cleancode.ecommerce.customer.domain.address.Charge;
import com.cleancode.ecommerce.customer.infra.persistence.address.ChargeEntity;
import com.cleancode.ecommerce.customer.infra.persistence.customer.CustomerEntity;

public final class ChargeMapper {

	private ChargeMapper() {}

	public static ChargeEntity toEntity(Charge charge, CustomerEntity customerEntity) {
		ChargeEntity entity = new ChargeEntity();

		entity.setPublic_id(charge.getPublicId().toString());
		entity.setMain(charge.isMain());
		entity.setReceiver(charge.getReceiver());
		entity.setStreet(charge.getStreet());
		entity.setNumber(charge.getNumber());
		entity.setNeighborhood(charge.getNeighborhood());
		entity.setZip_code(charge.getZipCode());
		entity.setObservation(charge.getObservation());
		entity.setStreet_type(charge.getStreetType());
		entity.setResidence_type(charge.getResidenceType());
		entity.setCity(charge.getCity());
		entity.setState(charge.getState());
		entity.setCountry(charge.getCountry());

		entity.setCustomer(customerEntity); 

		return entity;
	}

	public static void updateEntity(Charge charge, ChargeEntity entity) {
		entity.setReceiver(charge.getReceiver());
		entity.setMain(charge.isMain());
		entity.setStreet(charge.getStreet());
		entity.setNumber(charge.getNumber());
		entity.setNeighborhood(charge.getNeighborhood());
		entity.setZip_code(charge.getZipCode());
		entity.setObservation(charge.getObservation());
		entity.setStreet_type(charge.getStreetType());
		entity.setResidence_type(charge.getResidenceType());
		entity.setCity(charge.getCity());
		entity.setState(charge.getState());
		entity.setCountry(charge.getCountry());
	}

	public static Charge toDomain(ChargeEntity entity) {
		return new Charge(
			entity.getPublic_id(),
			entity.getMain(),
			entity.getReceiver(),
			entity.getStreet(),
			entity.getNumber(),
			entity.getNeighborhood(),
			entity.getZip_code(),
			entity.getObservation(),
			entity.getStreet_type(),
			entity.getResidence_type(),
			entity.getCity(),
			entity.getState(),
			entity.getCountry()
		);
	}
}
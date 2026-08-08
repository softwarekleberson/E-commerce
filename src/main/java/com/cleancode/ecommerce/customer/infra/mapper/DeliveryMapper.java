package com.cleancode.ecommerce.customer.infra.mapper;

import com.cleancode.ecommerce.customer.domain.address.Delivery;
import com.cleancode.ecommerce.customer.infra.persistence.address.DeliveryEntity;
import com.cleancode.ecommerce.customer.infra.persistence.customer.CustomerEntity;

public final class DeliveryMapper {

	private DeliveryMapper() {}

	public static DeliveryEntity toEntity(Delivery delivery, CustomerEntity customerEntity) {
		DeliveryEntity entity = new DeliveryEntity();

		entity.setPublic_id(delivery.getPublicId().toString());
		entity.setMain(delivery.isMain());
		entity.setReceiver(delivery.getReceiver());
		entity.setStreet(delivery.getStreet());
		entity.setNumber(delivery.getNumber());
		entity.setNeighborhood(delivery.getNeighborhood());
		entity.setZip_code(delivery.getZipCode());
		entity.setObservation(delivery.getObservation());
		entity.setStreet_type(delivery.getStreetType());
		entity.setResidence_type(delivery.getResidenceType());
		entity.setCity(delivery.getCity());
		entity.setState(delivery.getState());
		entity.setCountry(delivery.getCountry());
		entity.setDelivery_phrase(delivery.getDeliveryPhrase());

		entity.setCustomer(customerEntity);

		return entity;
	}

	public static void updateEntity(Delivery delivery, DeliveryEntity entity) {
		entity.setReceiver(delivery.getReceiver());
		entity.setMain(delivery.isMain());
		entity.setStreet(delivery.getStreet());
		entity.setNumber(delivery.getNumber());
		entity.setNeighborhood(delivery.getNeighborhood());
		entity.setZip_code(delivery.getZipCode());
		entity.setObservation(delivery.getObservation());
		entity.setStreet_type(delivery.getStreetType());
		entity.setResidence_type(delivery.getResidenceType());
		entity.setCity(delivery.getCity());
		entity.setState(delivery.getState());
		entity.setCountry(delivery.getCountry());
		entity.setDelivery_phrase(delivery.getDeliveryPhrase());
	}

	// Converte de entidade para domínio
	public static Delivery toDomain(DeliveryEntity entity) {
		return new Delivery(
			entity.getPublic_id(),
			entity.getMain(),
			entity.getDelivery_phrase(),
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
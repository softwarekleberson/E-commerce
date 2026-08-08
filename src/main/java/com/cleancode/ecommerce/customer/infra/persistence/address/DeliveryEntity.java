package com.cleancode.ecommerce.customer.infra.persistence.address;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_delivery")
public class DeliveryEntity extends AdrressEntity {

	private String delivery_phrase;
}

package com.cleancode.ecommerce.cart.infra.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_cart_reservation_settings")
public class CartReservationSettingsEntity {

	@Id
	private Integer settings_id;

	private int max_duration_minutes;
}

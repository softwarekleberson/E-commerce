package com.cleancode.ecommerce.customer.infra.persistence.card;

import java.time.LocalDate;

import com.cleancode.ecommerce.customer.infra.persistence.customer.CustomerEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "tb_card")
public class CardEntity {

	@Id
	private String card_id;
	private boolean main;
	private String printed_name;
	private String code;
	private String number_card;
	private LocalDate expiration_date;

	@Enumerated(EnumType.STRING)
	private FlagEntity flag;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	protected CustomerEntity customer;
}

package com.cleancode.ecommerce.payment.infra.persistencia;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Entity
@Table(name = "tb_description_payment")
public class DescriptionPaymentEntity {

	@Id
	private String id = UUID.randomUUID().toString();
		
	@Enumerated(EnumType.STRING)
	private TypePaymentEntity type_payment;
	
	@ManyToOne
	@JoinColumn(name = "payment_id")
	private PaymentEntity payment;
}

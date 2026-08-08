package com.cleancode.ecommerce.payment.infra.persistencia;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.cleancode.ecommerce.cart.infra.persistence.TypeCoinEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_payment")
public class PaymentEntity {

	@Id
	private String payment_id;
	
	private String customer_id;
	
	private LocalDateTime payment_date;
	
	private BigDecimal total;
	
	@Enumerated(EnumType.STRING)
	private TypeCoinEntity type_coin;
	
	@OneToMany(
			mappedBy = "payment",
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			fetch = FetchType.LAZY
	)
	private List<DescriptionPaymentEntity> description_payment = new ArrayList<>();

	private String order_id;
	
	@Enumerated(EnumType.STRING)
	private StatusPaymentEntity status_Payment;
}

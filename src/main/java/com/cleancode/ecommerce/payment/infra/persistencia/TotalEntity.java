package com.cleancode.ecommerce.payment.infra.persistencia;

import java.math.BigDecimal;

import com.cleancode.ecommerce.shared.kernel.TypeCoin;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TotalEntity {

	private BigDecimal total;
	
	@Enumerated(EnumType.STRING)
	private TypeCoin type_coin;
}

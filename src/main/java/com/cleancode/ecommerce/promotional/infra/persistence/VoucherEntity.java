package com.cleancode.ecommerce.promotional.infra.persistence;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_voucher")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoucherEntity {

	@Id
	@Column(name = "voucher_id", updatable = false, nullable = false)
	private String voucherId;

	@Column(name = "customer_id", nullable = false)
	private String customerId;

	private String message;
	private LocalDate emission;

	@Column(name = "type_voucher")
	@Enumerated(EnumType.STRING)
	private TypeVoucherEntity typeVoucher;

	private BigDecimal discount;
	
	private boolean active;
}

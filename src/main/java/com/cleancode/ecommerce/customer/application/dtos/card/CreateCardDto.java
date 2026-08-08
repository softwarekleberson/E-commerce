package com.cleancode.ecommerce.customer.application.dtos.card;

import java.time.LocalDate;

import com.cleancode.ecommerce.customer.domain.card.Flag;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateCardDto {

	private boolean main;

	@NotBlank(message = "Printed name is required")
	private String printedName;

	@NotBlank(message = "Code is required")
	@Size(min = 3, max = 4, message = "Code must have 3 or 4 digits")
	@Pattern(regexp = "\\d{3,4}", message = "Code must be numeric")
	private String code;

	@NotBlank(message = "Number card is required")
	private String numberCard;

	@NotNull(message = "Expiration date is required")
	@FutureOrPresent(message = "Expiration date must be in the present or future")
	private LocalDate expirationDate;

	@NotNull(message = "Accepted Flags: MASTERCARD, VISA, ELO")
	private Flag flag;

	public CreateCardDto(boolean main, String printedName, String code, String numberCard, LocalDate expirationDate,
			Flag flag) {

		this.main = main;
		this.printedName = printedName;
		this.code = code;
		this.numberCard = numberCard;
		this.expirationDate = expirationDate;
		this.flag = flag;
	}

	public boolean isMain() {
		return main;
	}

	public String getPrintedName() {
		return printedName;
	}

	public String getCode() {
		return code;
	}

	public String getNumberCard() {
		return numberCard;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public Flag getFlag() {
		return flag;
	}
}

package com.cleancode.ecommerce.adm.application.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateAdmDto {

	@NotBlank(message = "The Email not be null or invalid format")
	private String email;

	@NotBlank(message = "The Password not be null or invalid format")
	private String password;

	public CreateAdmDto(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
}

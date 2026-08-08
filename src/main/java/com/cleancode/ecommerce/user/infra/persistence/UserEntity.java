package com.cleancode.ecommerce.user.infra.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class UserEntity {

	@Id
	@Column(name = "user_id", updatable = false, nullable = false)
	private String user_id;
	
	@Column(name = "email", nullable = false, updatable = false, unique = true, length = 100)
	protected String email;

	@Column(name = "password", nullable = false, length = 255)
	protected String password;
}
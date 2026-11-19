package com.cleancode.ecommerce.adm.domain;

import java.util.Objects;

import com.cleancode.ecommerce.shared.kernel.domain.Email;
import com.cleancode.ecommerce.shared.kernel.domain.Password;
import com.cleancode.ecommerce.shared.kernel.domain.User;
import com.cleancode.ecommerce.shared.kernel.domain.UserId;

public class Adm extends User {

	public Adm(Email email, Password password) {
		super(Objects.requireNonNull(email), Objects.requireNonNull(password));
	}

	public Adm(UserId userId, Email email, Password password) {
		super(userId, email, password);
	}
}

package com.cleancode.ecommerce.customer.application.usecase;

import com.cleancode.ecommerce.customer.application.usecase.contract.PasswordValidationCheck;
import com.cleancode.ecommerce.shared.exception.IllegalPasswordException;
import com.cleancode.ecommerce.shared.kernel.Password;

public class PasswordValidationCheckImpl implements PasswordValidationCheck{

	@Override
	public void passwordCheckAndConfirmPassword(String password, String confirmPassword) {
		if (!password.equals(confirmPassword)) {
	        throw new IllegalPasswordException("Password does not match confirm password");
	    }
	}
	
	@Override
	public void validateAcceptablePasswordFormat(String password) {
		Password securePassword = new Password(password);
	}
}
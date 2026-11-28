package com.cleancode.ecommerce.shared.kernel.usecase;

import com.cleancode.ecommerce.shared.kernel.domain.Password;
import com.cleancode.ecommerce.shared.kernel.exception.IllegalPasswordException;
import com.cleancode.ecommerce.shared.kernel.usecase.contract.PasswordValidationCheck;

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
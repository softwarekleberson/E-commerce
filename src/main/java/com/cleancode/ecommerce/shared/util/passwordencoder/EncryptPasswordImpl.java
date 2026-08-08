package com.cleancode.ecommerce.shared.util.passwordencoder;

import com.cleancode.ecommerce.shared.util.passwordencoder.contract.PasswordEncoderService;
import com.cleancode.ecommerce.shared.util.passwordencoder.contract.Passwordencoder;

public class EncryptPasswordImpl implements Passwordencoder {

    private final PasswordEncoderService passwordEncoderService;

    public EncryptPasswordImpl(PasswordEncoderService passwordEncoderService) {
		this.passwordEncoderService = passwordEncoderService;
	}
    
    public String execute (String password) {
    	String encodePassword = passwordEncoderService.encode(password);
    	return encodePassword;
    }
}

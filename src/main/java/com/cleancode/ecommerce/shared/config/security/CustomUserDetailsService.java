package com.cleancode.ecommerce.shared.config.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cleancode.ecommerce.adm.domain.repository.AdmRepository;
import com.cleancode.ecommerce.customer.domain.customer.repository.CustomerRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	private final CustomerRepository clientRepo;
	private final AdmRepository adminRepo;

	public CustomUserDetailsService(CustomerRepository c, AdmRepository a) {
		this.clientRepo = c;
		this.adminRepo = a;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<UserDetails> userOpt = clientRepo.findByEmail(email).<UserDetails>map(c -> {
			return User.withUsername(c.getEmail().getEmail()).password(c.getPassword().getValue()).roles("CUSTOMER")
					.build();
		}).or(() -> adminRepo.findByEmail(email).map(a -> {
			return User.withUsername(a.getEmail()).password(a.getPassword()).roles("ADM").build();
		}));

		return userOpt.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}
}
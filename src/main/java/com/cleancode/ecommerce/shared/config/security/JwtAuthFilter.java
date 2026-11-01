package com.cleancode.ecommerce.shared.config.security;

import java.io.IOException;

import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;

	public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		String jwt = authHeader.substring(7);
		final String userEmail;
		try {
			userEmail = jwtService.extractUsername(jwt);
		} catch (JwtException | IllegalArgumentException exception) {
			rejectInvalidToken(response);
			return;
		}

		if (userEmail == null || userEmail.isBlank()) {
			rejectInvalidToken(response);
			return;
		}

		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			final UserDetails userDetails;
			try {
				userDetails = this.userDetailsService.loadUserByUsername(userEmail);
			} catch (UsernameNotFoundException exception) {
				rejectInvalidToken(response);
				return;
			}

			final boolean tokenValid;
			try {
				tokenValid = jwtService.isTokenValid(jwt, userDetails);
			} catch (JwtException | IllegalArgumentException exception) {
				rejectInvalidToken(response);
				return;
			}

			if (!tokenValid) {
				rejectInvalidToken(response);
				return;
			}

			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
					null, userDetails.getAuthorities());
			authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(authToken);
		}

		filterChain.doFilter(request, response);
	}

	private void rejectInvalidToken(HttpServletResponse response) throws IOException {
		SecurityContextHolder.clearContext();
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired bearer token");
	}
}

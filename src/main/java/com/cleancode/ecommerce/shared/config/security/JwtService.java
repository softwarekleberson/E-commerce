package com.cleancode.ecommerce.shared.config.security;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

	private final SecretKey secretKey;
	private final long expirationMs;

	public JwtService(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration-ms}") long expirationMs) {
		this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
		this.expirationMs = expirationMs;
	}

	public String generateToken(UserDetails userDetails) {
		Date now = new Date();
		Date expiration = new Date(now.getTime() + expirationMs);

		return Jwts.builder().setSubject(userDetails.getUsername())
				.claim("roles", userDetails.getAuthorities().stream().map(a -> a.getAuthority()).toList())
				.setIssuedAt(now).setExpiration(expiration).signWith(secretKey, SignatureAlgorithm.HS256).compact();
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
	}
}

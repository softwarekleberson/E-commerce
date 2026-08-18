package com.cleancode.ecommerce.shared.config.cors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

/*
 * Essa classe serve para configurar as regras globais de CORS
 * (Cross-Origin Resource Sharing) da API. O CORS é um mecanismo
 * de segurança dos navegadores que, por padrão, bloqueia requisições
 * HTTP vindas de origens (domínios ou portas) diferentes de onde a API
 * está hospedada.
 * 
 * */

@Configuration
public class GlobalCorsConfig {

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration config = new CorsConfiguration();

		config.setAllowedOriginPatterns(List.of(
			    "http://localhost:[*]",
			    "http://127.0.0.1:[*]"
			));
		
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

		config.setAllowedHeaders(List.of("*"));

		config.setAllowCredentials(true);

		config.addExposedHeader("Authorization");

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return new CorsFilter(source);
	}
}
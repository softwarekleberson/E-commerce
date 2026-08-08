package com.cleancode.ecommerce.shared.config.openApi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(info = @Info(title = "E-commerce API", version = "v1", description = "API do E-commerce desenvolvida com Arquitetura Limpa e autenticação JWT", contact = @Contact(name = "Kleberson dos Santos Silva")), security = {
		@SecurityRequirement(name = "jwt_auth") }
)
@SecurityScheme(name = "jwt_auth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class OpenApiConfig {

}

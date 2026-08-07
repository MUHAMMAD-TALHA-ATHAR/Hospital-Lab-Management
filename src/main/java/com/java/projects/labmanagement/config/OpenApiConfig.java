package com.java.projects.labmanagement.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger / OpenAPI Configuration
 * This class configures how the API documentation looks in Swagger UI
 * It adds JWT Bearer token support so you can authorize requests directly from Swagger.
 */
@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI labManagementOpenAPI() {
        return new OpenAPI()
                // This enables the "Authorize" button in Swagger UI for JWT
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))

                .info(new Info()
                        .title("Lab Management System API")
                        .version("v1.0")
                        .description("Lab test booking, status tracking and report management system")
                        .contact(new Contact()
                                .name("Your Name")
                                .email("your.email@example.com"))
                        .license(new License()
                                .name("Portfolio Project")))

                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SECURITY_SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Enter JWT token after login")
                        )
                );
    }
}
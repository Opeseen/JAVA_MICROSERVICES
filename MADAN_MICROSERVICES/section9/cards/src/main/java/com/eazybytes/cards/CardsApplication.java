package com.eazybytes.cards;

import com.eazybytes.cards.dto.CardsContactInfoDTO;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {CardsContactInfoDTO.class})
@OpenAPIDefinition(info = @Info(
		title = "Card Microservice REST API Documentation",
		description = "EazyBank Account Microservice REST API Documentation",
		version = "v1",
		contact = @Contact(name="Opeyemi Alabi", email = "Opeyemi5055@yahoo.com", url = "opeyemidev.com"),
		license = @License(name = "Apache2.0", url = "https://opeyemidev.com")),
		externalDocs = @ExternalDocumentation(description = "EazyBank microservice", url = "http://localhost:8090/swagger-ui/index.html")

)
public class CardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardsApplication.class, args);
	}

}

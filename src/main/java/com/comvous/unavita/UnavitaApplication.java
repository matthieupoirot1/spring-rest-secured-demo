package com.comvous.unavita;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
@OpenAPIDefinition(info = @Info(title = "My API", version = "v1"))
@SecurityScheme(
		name = "basicAuth",
		type = SecuritySchemeType.HTTP,
		scheme = "bearer"
)
public class UnavitaApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnavitaApplication.class, args);
	}

}

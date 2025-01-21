package com.task.library.management.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {


	@Bean
	public OpenAPI myCustomConfing() {
		return new OpenAPI()
			.info(
					new Info().title("Library-Management-API")
					.description("By Lakshminarayan")
			)
		
//		.servers(Arrays.asList(
//                new Server().url("http://localhost:8081").description("local"),
//                new Server().url("http://localhost:8082").description("live")
//        ));'
		
			.addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
			.components(new Components()
	            .addSecuritySchemes("bearerAuth", new SecurityScheme()
	                .type(SecurityScheme.Type.HTTP)
	                .scheme("bearer")
	                .bearerFormat("JWT")
	                .in(SecurityScheme.In.HEADER)
	                .name("Authorization")));

	}
}




package com.akash.blog;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot Blog App REST APIs",
				description = "Spring Boot Blog App REST APIs Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Akash",
						email = "akash1018.be2@chitkarauniversity.edu.in",
						url = "https://www.linkedin.com/in/akash-mandal-52298123a/"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.linkedin.com/in/akash-mandal-52298123a/license"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot Blog App REST APIs",
				url="https://github.com/AkashMandal2003/Blogging-Website-REST-API"
		)
)
public class BloggingWebsiteRestApiApplication {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(BloggingWebsiteRestApiApplication.class, args);
	}

}

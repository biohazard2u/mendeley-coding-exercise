package com.mendeley.coding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@Configuration
public class App {
	
	private static final String PROJECT_VERSION = "1.0";
	private static final String PROJECT_TITLE = "Mendeley coding exercise";
	private static final String PROJECT_DESCRIPTION = "This Mendeley coding exercise has been written by Marcos Zalacain";

	public static void main(String[] args) { 
		SpringApplication.run(App.class, args);
	}
	
	@Bean
	public Docket newsApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("mendeley-coding-exercise")
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors
						.basePackage("com.mendeley.coding.controller"))
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(PROJECT_TITLE)
				.description(PROJECT_DESCRIPTION).version(PROJECT_VERSION)
				.build();
	}
}

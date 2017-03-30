package com.ironyard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@EnableSwagger2
@SpringBootApplication
public class BarappApplication {

	public static void main(String[] args) {
		SpringApplication.run(BarappApplication.class, args);
	}

	@Bean
	public Docket BarApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("Bar Locator api")
				.apiInfo(apiInfo())
				.select()
				.paths(regex("/barLocation/*.*"))
				.build();



	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Find a Bar Nearby")
				.description("Welcome")
				.termsOfServiceUrl("http://theironyard.com")
				.contact("Rohan Ayub")
				.license("Apache License Version 2.0")
				.licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE")
				.version("2.1")
				.build();

	}
}

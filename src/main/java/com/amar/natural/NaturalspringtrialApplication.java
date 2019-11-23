package com.amar.natural;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.amar.natural.util.EmailQueueDataInserter;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class NaturalspringtrialApplication {

	public static void main(String[] args) {
		SpringApplication.run(NaturalspringtrialApplication.class, args);
		EmailQueueDataInserter emailSender = EmailQueueDataInserter.getElasticDataInserter();
        Thread emailSenderThread = new Thread(emailSender);
        emailSenderThread.start();
	}
	
	@Bean
	public Docket swaggerConfiguration() {
		// Here will do swagger configuration
		
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.amar.natural")).build();
	}
	
	private ApiInfo apiDetails() {
		
		return new ApiInfo("News App apis", "Apis for news app", "1.0", "Free to use", "Amar Latthe", "", "");
	}

}

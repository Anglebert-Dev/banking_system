package com.anglebert.bankingsystem;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info=@Info(
				title = "The Java Academy Bank App",
				description = "Backend Rest APIs for Banking System",
				version = "v1.0",
				contact = @Contact(
						name = "Anglebert Ish",
						email = "anglebertsh@gmail.com"
//						url=
				)
//			license = @License(
////					desc=
//			)
		)
//		externalDocs = @ExternalDocumentation()

)
public class BankingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingSystemApplication.class, args);
	}

}

package com.zss.bookstore;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "ZSS Bookstore",
                description = "ZSS Bookstore REST API Documentation",
                version = "v1",
                license = @License(
                        name = "ZSS"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "ZSS Bookstore REST API Documentation",
                url = "http://localhost:8085/swagger-ui/index.html#/"
        )
)
public class ZssBookstoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZssBookstoreApplication.class, args);
    }

}

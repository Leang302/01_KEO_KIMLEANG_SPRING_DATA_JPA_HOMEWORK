package org.example.hwspringdatajpa;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Homework Spring Data JPA",
        version = "v1",
        description = "This API provides endpoints for managing customers, products, and orders using Spring Data JPA.")
//        servers = @Server(url = "/")
)
public class HwSpringDataJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(HwSpringDataJpaApplication.class, args);
    }

}

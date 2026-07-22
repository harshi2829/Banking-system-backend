package com.example.bankingsystem;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@OpenAPIDefinition(
        info=@Info(
                title= "Auth Service API",
                version = "1.0",
                description = "Banking System - Authentication Service"
        )
)
@SpringBootApplication
public class BankingSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(BankingSystemApplication.class, args);
    }
}
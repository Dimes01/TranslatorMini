package com.example.tink_lab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class TinkoffLabApplication {

	public static void main(String[] args) {
		SpringApplication.run(TinkoffLabApplication.class, args);
	}
}

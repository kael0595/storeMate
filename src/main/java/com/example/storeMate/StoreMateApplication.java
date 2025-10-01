package com.example.storeMate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class StoreMateApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreMateApplication.class, args);
	}

}

package com.zip9.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Zip9Application {

	public static void main(String[] args) {
		SpringApplication.run(Zip9Application.class, args);
	}

}

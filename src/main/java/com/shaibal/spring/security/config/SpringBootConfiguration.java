package com.shaibal.spring.security.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.shaibal.spring.security")
public class SpringBootConfiguration {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootConfiguration.class, args);
	}
}

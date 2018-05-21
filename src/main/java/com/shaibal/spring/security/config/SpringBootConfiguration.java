package com.shaibal.spring.security.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "com.shaibal.spring.security")
public class SpringBootConfiguration extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootConfiguration.class, args);
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringBootConfiguration.class);
    }
}
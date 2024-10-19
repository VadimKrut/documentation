package com.service.documentation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(
		scanBasePackages = {
				"com.service.documentation", "com.service.documentation.configuration", "com.service.documentation.controller", "com.service.documentation.exception"
		}
)
@ConfigurationPropertiesScan
@EnableConfigurationProperties
public class DocumentationApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(DocumentationApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(DocumentationApplication.class, args);
	}
}
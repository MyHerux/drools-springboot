package com.xu.drools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages ="com.xu.drools")
@EnableScheduling
public class DroolsSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(DroolsSpringbootApplication.class, args);
	}
}

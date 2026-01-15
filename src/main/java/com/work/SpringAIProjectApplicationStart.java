package com.work;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.work"})
public class SpringAIProjectApplicationStart {
	public static void main(String[] args) {
		SpringApplication.run(SpringAIProjectApplicationStart.class, args);
	}

}
package com.digytal.control;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ControlApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControlApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner run() throws Exception {
		return args -> {
			System.out.println("SE PRECISAR EXECUTAR ALGO NO CONSOLE");
		};
	}
}

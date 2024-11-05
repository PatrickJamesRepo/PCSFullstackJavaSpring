package com.example.purrtycatsfullstack;

import org.springframework.context.annotation.PropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@PropertySource("file:.env")
public class PurrtyCatsFullStackApplication {
	public static void main(String[] args) {
		SpringApplication.run(PurrtyCatsFullStackApplication.class, args);
	}
}

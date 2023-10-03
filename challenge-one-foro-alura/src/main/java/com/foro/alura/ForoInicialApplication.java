package com.foro.alura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
//@EntityScan(basePackages = "com.foro.alura.modelo")
public class ForoInicialApplication  {

	public static void main(String[] args) {
		SpringApplication.run(ForoInicialApplication.class, args);
	}

}

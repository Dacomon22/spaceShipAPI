package com.example.spaceship;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class SpaceShipApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpaceShipApiApplication.class, args);
	}

}

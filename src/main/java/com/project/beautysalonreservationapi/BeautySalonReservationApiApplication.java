package com.project.beautysalonreservationapi;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.TimeZone;

@SpringBootApplication
public class BeautySalonReservationApiApplication {
	@PostConstruct
	public void init(){
		// Setting Spring Boot SetTimeZone
		TimeZone.setDefault(TimeZone.getTimeZone("EST"));
	}

	public static void main(String[] args) {
		SpringApplication.run(BeautySalonReservationApiApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		
		return new ModelMapper();
	}


}

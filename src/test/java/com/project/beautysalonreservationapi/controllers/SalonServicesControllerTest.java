package com.project.beautysalonreservationapi.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.beautysalonreservationapi.models.SalonService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SalonServicesControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	void shouldReturnASalonServiceWhenDataIsSaved() {
		ResponseEntity<SalonService> response = restTemplate.getForEntity("/services/1",
				SalonService.class);
		//test for status code
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		//test for body
		SalonService body = response.getBody();
		assertThat(body).isNotNull();
		
		//test for return data
		assertThat(body.getId()).isEqualTo(1l);
		assertThat(body.getName()).isEqualTo("Haircut");
		assertThat(body.getDescription()).isEqualTo("A basic haircut for any hair length.");
		assertThat(body.getPrice()).isEqualTo(25.0d);
		
	}
	
	
	@Test
	void shouldReturnAllServicesWithoutRange() throws JsonMappingException, JsonProcessingException {
		ResponseEntity<String> response = restTemplate.getForEntity("/services",
				String.class);
		//should return 200 OK status
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		//not empty body
		assertThat(response.getBody()).isNotNull();
		List<SalonService> services = objectMapper.readValue(response.getBody(), 
				new TypeReference<List<SalonService>>() {});
		
		//check count
		assertThat(services.size()).isEqualTo(6);
		
		//should return correct data
	}
	

}

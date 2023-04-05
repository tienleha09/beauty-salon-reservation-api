package com.project.beautysalonreservationapi;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import com.project.beautysalonreservationapi.models.SalonService;

@JsonTest
public class SalonServicesJsonTest {
	
	@Autowired
	private JacksonTester<SalonService> json;
	
	@Test
	public void salonServicesSerializationTest() throws IOException {
		SalonService service = new SalonService("Haircut", "A classic haircut for men or women",50.0);
		service.setId(1l);
		assertThat(json.write(service)).isEqualToJson("expected.json");
		//check if it has the right fields and values are matched
		assertThat(json.write(service)).hasJsonPathNumberValue("@.id");
		assertThat(json.write(service)).extractingJsonPathNumberValue("@.id").isEqualTo(1);
		
		assertThat(json.write(service)).hasJsonPathStringValue("@.name");
		assertThat(json.write(service)).extractingJsonPathStringValue("@.name").isEqualTo("Haircut");
		
		assertThat(json.write(service)).hasJsonPathStringValue("@.description");
		assertThat(json.write(service)).extractingJsonPathStringValue("@.description").isEqualTo("A classic haircut for men or women");
		
		assertThat(json.write(service)).hasJsonPathNumberValue("@.price");
		assertThat(json.write(service)).extractingJsonPathNumberValue("@.price").isEqualTo(50.0);
	}
	
	@Test
	public void salonServiceDeserializationTest() throws IOException{
		String expected = """
				 {
				  	"id": 1,
				  	"name": "Haircut",
				  	"description": "A classic haircut for men or women",
				  	"price": 50.0
					}
				""";
		//assertThat(json.parse(expected)).isEqualTo(new SalonService(1l,"Haircut", "A classic haircut for men or women",50.0));
		
		assertThat(json.parseObject(expected).getId()).isEqualTo(1l);
		assertThat(json.parseObject(expected).getName()).isEqualTo("Haircut");
		assertThat(json.parseObject(expected).getDescription()).isEqualTo("A classic haircut for men or women");
		assertThat(json.parseObject(expected).getPrice()).isEqualTo(50.0);
	}
}

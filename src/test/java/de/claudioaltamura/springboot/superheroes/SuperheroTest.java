package de.claudioaltamura.springboot.superheroes;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

@JsonTest
class SuperheroTest {

	@Autowired
	private JacksonTester<Superhero> json;

	@Test
	void serialize() throws IOException {
		Superhero superhero = new Superhero("Batman", "Bruce Wayne", 98.0d);

		JsonContent<Superhero> result = this.json.write(superhero);

		assertThat(result).hasJsonPathStringValue("$.name");
		assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("Batman");
	}

	@Test
	void deserialize() throws IOException {
		String jsonContent =
				"{\"name\":\"Batman\",\"realName\":\"Bruce Wayne\",\"power\": 100.0}";

		Superhero result = this.json.parse(jsonContent).getObject();

		assertThat(result.getName()).isEqualTo("Batman");
		assertThat(result.getPower()).isEqualTo(100.0d);
	}
}

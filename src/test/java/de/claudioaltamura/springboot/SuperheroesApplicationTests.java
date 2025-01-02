package de.claudioaltamura.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SuperheroesApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void shouldReturnAllSuperheroesWhenGet() {
		this.webTestClient
				.get()
				.uri("/api/v1/superheroes")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk();
	}


	@Test
	void shouldReturnSuperheroWhenPathParameterPassed() {
		this.webTestClient
				.get()
				.uri("/api/v1/superheroes/1")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk();
	}

	@Test
	void shouldReturnNotFoundWhenNotExistingId() {
		this.webTestClient
				.get()
				.uri("/api/v1/superheroes/4")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isNotFound();
	}

	@Test
	void shouldAddNewSuperheroWhenPost() {
		var newSuperhero = "{\"name\":\"Hero\",\"realName\":\"real name\",\"power\":90.0}";
		this.webTestClient.post()
				.uri("/api/v1/superheroes")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(newSuperhero)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isCreated();
	}

}

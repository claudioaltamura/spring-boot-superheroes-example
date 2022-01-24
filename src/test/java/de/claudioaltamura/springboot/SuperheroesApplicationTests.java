package de.claudioaltamura.springboot;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class SuperheroesApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void getAllSuperheroes() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/superheroes")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	void getSuperhero() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/superheroes/1")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	void returnsNotFoundForInvalidId() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/superheroes/4")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn();
	}

	@Test
	void addsNewSuperHero() throws Exception {
		String newSuperhero = "{\"name\":\"Hero\",\"realName\":\"real name\",\"power\":90.0}";
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/superheroes")
						.contentType(MediaType.APPLICATION_JSON)
						.content(newSuperhero)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andReturn();
	}

}

package de.claudioaltamura.superheroes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SuperHeroesApplicationIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllSuperHeroes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/superheroes")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void getSuperHero() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/superheroes/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void returnsNotFoundForInvalidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/superheroes/4")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void addsNewSuperHero() throws Exception {
        String newRide = "{\"name\":\"Hero\",\"realName\":\"real name.\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/superheroes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newRide)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
}

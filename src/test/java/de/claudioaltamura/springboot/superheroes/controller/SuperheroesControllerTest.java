package de.claudioaltamura.springboot.superheroes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.claudioaltamura.springboot.superheroes.Superhero;
import de.claudioaltamura.springboot.superheroes.SuperheroService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Web slice test with MockMVC. MockMVC prepares a fake web application context to mock the HTTP
 * requests and responses, it may not support all the features of a full-blown Spring application.
 * E.g. HTTP redirections are not supported.
 */
@WebMvcTest(SuperheroesController.class)
class SuperheroesControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SuperheroService superheroService;

    @Test
    void shouldReturnSuperheroWhenPost() throws Exception {
        final Superhero superheroRequest = Superhero.builder().name("Batman").realName("Bruce Wayne").power(90.0d).build();
        final Superhero superheroResponse =
                Superhero.builder().id(1L).name("Batman").realName("Bruce Wayne").power(90.0d).build();
        when(superheroService.save(any(Superhero.class))).thenReturn(superheroResponse);
        mockMvc
                .perform(
                        post("/api/v1/superheroes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(superheroRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/v1/superheroes/1"))
                .andExpect(jsonPath("$.name").value("Batman"))
                .andReturn();

        ArgumentCaptor<Superhero> superheroRequestArgumentCaptor =
                ArgumentCaptor.forClass(Superhero.class);

        verify(superheroService).save(superheroRequestArgumentCaptor.capture());
    }

    @Test
    void shouldThrownAValidationErrorWhenPost() throws Exception {
        String invalidSuperheroRequest =
                "{\"name\":\"Batman\",\"power\":100.1,\"realName\":\"Bruce Wayne\"}";

        mockMvc
                .perform(
                        post("/api/v1/superheroes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(invalidSuperheroRequest))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void shouldReturnNoContentWhenDeleteExistingSuperhero() throws Exception {
        doNothing().when(superheroService).deleteById(anyLong());

        mockMvc
                .perform(delete("/api/v1/superheroes/{superheroesId}", 1))
                .andExpect(status().isNoContent());

        verify(superheroService).deleteById(anyLong());
    }

}
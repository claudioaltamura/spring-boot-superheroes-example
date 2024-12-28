package de.claudioaltamura.springboot.superheroes.repository;

import de.claudioaltamura.springboot.superheroes.entity.SuperheroEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql("superheroes.sql")
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
  })
class SuperheroRepositoryTest {

    @Autowired
    private SuperheroRepository superheroRepository;

    @Test
    void shouldReturnSuperheroWhenFindByName() {
        final List<SuperheroEntity> superheroes = superheroRepository.findByName("Spider-Man");

        assertThat(superheroes).hasSize(1);
    }

    @Test
    void shouldReturnSuperheroWhenGetByPageNumberAndSize() {
        final Page<SuperheroEntity> superheroes = superheroRepository.findAll(PageRequest.of(0,10));

        assertThat(superheroes).hasSize(1);
    }

    //getById(@NotNull Long id);
    //existsById(@NotNull  Long id);
    //findByName(@NotNull String name);
    //save(@Valid Superhero superhero);
    //update(@Valid Superhero superhero);
    //deleteById(@NotNull Long id);
    //deleteAll();

}
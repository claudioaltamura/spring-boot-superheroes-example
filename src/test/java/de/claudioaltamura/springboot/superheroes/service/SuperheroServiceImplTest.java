package de.claudioaltamura.springboot.superheroes.service;

import de.claudioaltamura.springboot.superheroes.Superhero;
import de.claudioaltamura.springboot.superheroes.entity.SuperheroEntity;
import de.claudioaltamura.springboot.superheroes.repository.SuperheroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SuperheroServiceImplTest {

    private SuperheroRepository superheroRepositoryMock;

    private final SuperheroMapper superheroMapper = new SuperheroMapper();

    private SuperheroServiceImpl superheroService;

    private SuperheroEntity createEntity() {
        return new SuperheroEntity(1L,"Batman","Bruce Wayne",90.0d);
    }
    private Superhero createDto() {
        return superheroMapper.toDto(createEntity());
    }

    @BeforeEach
    public void setUp() {
        superheroRepositoryMock = Mockito.mock(SuperheroRepository.class);
        superheroService = new SuperheroServiceImpl(superheroRepositoryMock, superheroMapper);
    }

    @Test
    void shouldReturnAll() {
        final var list = List.of(createEntity());
        when(superheroRepositoryMock.findAll()).thenReturn(list);
        assertThat(superheroService.getAll()).hasSize(1);
        verify(superheroRepositoryMock, times(1)).findAll();
    }

    @Test
    void shouldReturnSuperheroById() {
        final var sample = createEntity();
        when(superheroRepositoryMock.findById(1L)).thenReturn(Optional.of(sample));
        //noinspection OptionalGetWithoutIsPresent
        assertThat(superheroService.getById(1L)).contains(createDto());
        verify(superheroRepositoryMock, times(1)).findById(1L);
    }

    @Test
    void shouldReturnSuperheroByName() {
        final var list = List.of(createEntity());
        when(superheroRepositoryMock.findByName("Batman")).thenReturn(list);
        assertThat(superheroService.findByName("Batman")).contains(createDto());
        verify(superheroRepositoryMock, times(1)).findByName("Batman");
    }

    @Test
    void shouldSaveSuperhero() {
        final var sample = Superhero.builder().id(1L).name("Batman").realName("Bruce Wayne").power(90.0d).build();
        when(superheroRepositoryMock.save(any())).thenReturn(createEntity());
        Superhero savedHero = superheroService.save(sample);
        assertThat(savedHero.getId()).isEqualTo(createDto().getId());
        verify(superheroRepositoryMock, times(1)).save(any());
    }

}
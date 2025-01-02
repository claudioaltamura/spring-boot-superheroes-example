package de.claudioaltamura.springboot.superheroes.service;

import de.claudioaltamura.springboot.superheroes.Superhero;
import de.claudioaltamura.springboot.superheroes.entity.SuperheroEntity;
import de.claudioaltamura.springboot.superheroes.repository.SuperheroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

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
        verifyNoMoreInteractions(superheroRepositoryMock);
    }

    @Test
    void shouldReturnPage() {
        final var page = 0;
        final var size = 10;
        final var list = List.of(createEntity());
        final var pageRequest = PageRequest.of(page, size);
        when(superheroRepositoryMock.findAll(pageRequest)).thenReturn(new PageImpl<>(list,pageRequest,list.size()));
        assertThat(superheroService.get(page, size)).hasSize(1);
        verify(superheroRepositoryMock, times(1)).findAll(pageRequest);
        verifyNoMoreInteractions(superheroRepositoryMock);
    }

    @Test
    void shouldReturnSuperheroById() {
        final var sample = createEntity();
        when(superheroRepositoryMock.findById(1L)).thenReturn(Optional.of(sample));
        assertThat(superheroService.getById(1L)).contains(createDto());
        verify(superheroRepositoryMock, times(1)).findById(1L);
        verifyNoMoreInteractions(superheroRepositoryMock);
    }

    @Test
    void shouldReturnSuperheroByName() {
        final var list = List.of(createEntity());
        when(superheroRepositoryMock.findByName("Batman")).thenReturn(list);
        assertThat(superheroService.findByName("Batman")).contains(createDto());
        verify(superheroRepositoryMock, times(1)).findByName("Batman");
        verifyNoMoreInteractions(superheroRepositoryMock);
    }

    @Test
    void shouldSaveSuperhero() {
        final var sample = Superhero.builder().id(1L).name("Batman").realName("Bruce Wayne").power(90.0d).build();
        when(superheroRepositoryMock.save(any())).thenReturn(createEntity());
        Superhero savedHero = superheroService.save(sample);
        assertThat(savedHero.getId()).isEqualTo(createDto().getId());
        verify(superheroRepositoryMock, times(1)).save(any());
        verifyNoMoreInteractions(superheroRepositoryMock);
    }

    @Test
    void shouldUpdateSuperhero() {
        final var superheroSaved = createEntity();
        when(superheroRepositoryMock.findById(1L)).thenReturn(Optional.of(superheroSaved));

        final var superheroUpdated = createEntity();
        superheroUpdated.setPower(90.1d);
        when(superheroRepositoryMock.save(any())).thenReturn(superheroUpdated);
        final var superheroToBeUpdated = Superhero.builder().id(1L).name("Batman").realName("Bruce Wayne").power(90.1d).build();

        final var updatedHero = superheroService.update(superheroToBeUpdated);

        assertThat(updatedHero.getPower()).isEqualTo(90.1d);
        verify(superheroRepositoryMock, times(1)).findById(any());
        verify(superheroRepositoryMock, times(1)).save(any());
        verifyNoMoreInteractions(superheroRepositoryMock);
    }
}
package de.claudioaltamura.springboot.superheroes.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import de.claudioaltamura.springboot.superheroes.Superhero;
import de.claudioaltamura.springboot.superheroes.SuperheroNotFoundException;
import de.claudioaltamura.springboot.superheroes.SuperheroService;
import de.claudioaltamura.springboot.superheroes.entity.SuperheroEntity;
import de.claudioaltamura.springboot.superheroes.repository.SuperheroRepository;

@Transactional
@Service
@RequiredArgsConstructor
public class SuperheroServiceImpl implements SuperheroService {

	private final SuperheroRepository superheroRepository;
	private final SuperheroMapper superheroMapper;

	public List<Superhero> getAll() {
		return superheroRepository.findAll().stream().map(superheroMapper::toDto).collect(Collectors.toList());
	}

	public Optional<Superhero> getById(@NotNull Long id) {
		return superheroRepository.findById(id).map(superheroMapper::toDto);
	}

	public boolean existsById(@NotNull Long id) {
		return superheroRepository.existsById(id);
	}

	public List<Superhero> findByName(@NotNull String name) {
		return superheroRepository.findByName(name).stream().map(superheroMapper::toDto).collect(Collectors.toList());
	}

	public Superhero save(@Valid Superhero superhero) {
		SuperheroEntity toBeCreated = new SuperheroEntity();
		toBeCreated.setName(superhero.getName());
		toBeCreated.setPower(superhero.getPower());
		toBeCreated.setRealName(superhero.getRealName());
		final var createdSuperhero = superheroRepository.save(toBeCreated);
		return superheroMapper.toDto(createdSuperhero);
	}

	public Superhero update(@Valid Superhero superhero) {
		SuperheroEntity toBeUpdated = new SuperheroEntity();
		toBeUpdated.setName(superhero.getName());
		toBeUpdated.setPower(superhero.getPower());
		toBeUpdated.setRealName(superhero.getRealName());
		final var updatedSuperhero = superheroRepository.save(toBeUpdated);
		return superheroMapper.toDto(updatedSuperhero);
	}

	public void deleteById(@NotNull Long id) {
		if(!superheroRepository.existsById(id)) {
			throw new SuperheroNotFoundException(String.format("Superhero (id=%d) not found.", id));
		}
		superheroRepository.deleteById(id);
	}

	public void deleteAll() {
		superheroRepository.deleteAll();
	}

}

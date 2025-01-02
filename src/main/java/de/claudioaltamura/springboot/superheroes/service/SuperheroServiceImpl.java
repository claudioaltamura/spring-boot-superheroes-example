package de.claudioaltamura.springboot.superheroes.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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

	@Override
	public List<Superhero> get(Integer page, Integer size) {
		var pageRequest = PageRequest.of(sanitizedPage(page,size), sanitizeSize(size));
		var superheroes = superheroRepository.findAll(pageRequest);
		return superheroes.stream().map(superheroMapper::toDto).collect(Collectors.toList());
	}

	int sanitizedPage(int page, int size) {
		int maxPage = calcMaxPage(size);
		var sanitizedPage = page;
		if(page < 0) {
			sanitizedPage = 0;
		}
		if(page > maxPage) {
			sanitizedPage = maxPage;
		}
		return sanitizedPage;
	}

	int calcMaxPage(Integer size) {
		long count = superheroRepository.count();
		return count % size == 0 ? (int) count / size : (int) count / (size + 1);
	}

	int sanitizeSize(Integer size) {
		var sanitizedSize = size;
		if(size < 0) {
			sanitizedSize = 0;
		}
		if(size>100) {
			sanitizedSize = 100;
		}
		return sanitizedSize;
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
		return superheroRepository.findById(superhero.getId())
				.map(toBeUpdated -> {
					toBeUpdated.setName(superhero.getName());
					toBeUpdated.setPower(superhero.getPower());
					toBeUpdated.setRealName(superhero.getRealName());
					final var updatedSuperHero = superheroRepository.save(toBeUpdated);
					return superheroMapper.toDto(updatedSuperHero);
				})
				.orElseThrow(()-> new SuperheroNotFoundException(String.format("Superhero (id=%d) not found.", superhero.getId())));
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

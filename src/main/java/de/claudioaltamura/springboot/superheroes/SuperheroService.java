package de.claudioaltamura.springboot.superheroes;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface SuperheroService {

	List<Superhero> getAll();

	Optional<Superhero> getById(@NotNull Long id);

	boolean existsById(@NotNull  Long id);

	List<Superhero> findByName(@NotNull String name);

	Superhero save(@Valid Superhero superhero);

	Superhero update(@Valid Superhero superhero);

	void deleteById(@NotNull Long id);

	void deleteAll();

}

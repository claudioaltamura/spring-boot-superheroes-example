package de.claudioaltamura.springboot.superheroes.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.claudioaltamura.springboot.superheroes.SuperheroNotFoundException;
import de.claudioaltamura.springboot.superheroes.Superhero;
import de.claudioaltamura.springboot.superheroes.SuperheroService;

@RestController
@RequestMapping("/api/v1")
public class SuperheroController {

	private final SuperheroService superheroService;

	public SuperheroController(SuperheroService superheroService) {
		this.superheroService = superheroService;
	}

	@GetMapping("/superheroes")
	public ResponseEntity<List<Superhero>> getAll() {
		List<Superhero> superheroes = superheroService.getAll();
		return new ResponseEntity<>(superheroes, HttpStatus.OK);
	}

	@Operation(summary = "Get a superhero by its id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the superhero",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Superhero.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Superhero not found",
					content = @Content) })
	@GetMapping("/superheroes/{id}")
	public ResponseEntity<Superhero> getById(@Parameter(description = "id of superhero") @PathVariable("id") long id) {
		return new ResponseEntity<>(superheroService.getById(id), HttpStatus.OK);
	}

	@Operation(summary = "Create a superhero")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "created superhero",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Superhero.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid superhero supplied.",
					content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server error",
					content = @Content) })
	@PostMapping("/superheroes")
	public ResponseEntity<Superhero> createSuperhero(@RequestBody @Valid Superhero superhero) {
		Superhero newSuperhero = superheroService.save(superhero);
		return new ResponseEntity<>(newSuperhero, HttpStatus.CREATED);
	}

	@PutMapping("/superheroes/{id}")
	public ResponseEntity<Superhero> updateSuperhero(@PathVariable("id") long id, @RequestBody @Valid Superhero superhero) {
		if(superheroService.existsById(id)) {
			return new ResponseEntity<>(superheroService.update(superhero), HttpStatus.OK);
		} else {
			throw new SuperheroNotFoundException(String.format("Superhero (id=%d) not found.", id));
		}
	}

	@DeleteMapping("/superheroes/{id}")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
		superheroService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/superheroes")
	public ResponseEntity<HttpStatus> deleteAll() {
		superheroService.deleteAll();
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/superheroes/search")
	public ResponseEntity<List<Superhero>> findByName(@RequestParam(defaultValue = "") String name) {
		List<Superhero> superheroes = superheroService.findByName(name);
		return new ResponseEntity<>(superheroes, HttpStatus.OK);
	}
}

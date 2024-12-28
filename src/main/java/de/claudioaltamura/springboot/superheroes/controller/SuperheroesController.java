package de.claudioaltamura.springboot.superheroes.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SuperheroesController {

	private final SuperheroService superheroService;

	@GetMapping("/superheroes")
	@Operation(summary = "Get superheroes", tags = "Superhero")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "list of superheroes",
				content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Superhero.class)))}
		),
		@ApiResponse(responseCode = "500", description = "Internal Server error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)) }
		)}
	)
	public ResponseEntity<List<Superhero>> get(
			@RequestParam(defaultValue = "0") final Integer pageNumber,
			@RequestParam(defaultValue = "10") final Integer size
	) {
		return ResponseEntity.ok(superheroService.get(pageNumber, size));
	}

	@GetMapping("/superheroes/{id}")
	@Operation(summary = "Get a superhero by id", tags = "Superhero")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the superhero",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Superhero.class)) }),
			@ApiResponse(responseCode = "400", description = "bad request",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Error.class)) }),
			@ApiResponse(responseCode = "404", description = "Superhero not found",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Error.class)) }),
			@ApiResponse(responseCode = "500", description = "Internal Server error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)) })
			})
	public ResponseEntity<Superhero> getById(@Parameter(description = "id of superhero") @PathVariable("id") long id) {
		final var superhero = superheroService.getById(id).orElseThrow(() -> new SuperheroNotFoundException(String.format("Superhero (id=%d) not found.", id)));
		return new ResponseEntity<>(superhero, HttpStatus.OK);
	}

	@PostMapping("/superheroes")
	@Operation(summary = "Creates a superhero", tags = "Superhero")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "created superhero",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Superhero.class)) }),
			@ApiResponse(responseCode = "400", description = "bad request",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Error.class)) }),
			@ApiResponse(responseCode = "500", description = "Internal Server error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)) })
	})
	public ResponseEntity<Superhero> createSuperhero(@RequestBody @Valid Superhero superhero) {
		final var newSuperhero = superheroService.save(superhero);
		final var location =
				ServletUriComponentsBuilder.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(newSuperhero.getId())
						.toUri();

		return ResponseEntity.created(location).body(newSuperhero);
	}

	@PutMapping("/superheroes/{id}")
	@Operation(summary = "Updates a superhero", tags = "Superhero")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "updated superhero",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Superhero.class)) }),
			@ApiResponse(responseCode = "400", description = "bad request",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Error.class)) }),
			@ApiResponse(responseCode = "404", description = "Superhero not found",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Error.class)) }),
			@ApiResponse(responseCode = "500", description = "Internal Server error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)) })

	})
	public ResponseEntity<Superhero> updateSuperhero(@PathVariable("id") long id, @RequestBody @Valid Superhero superhero) {
		if(superheroService.existsById(id)) {
			return new ResponseEntity<>(superheroService.update(superhero), HttpStatus.OK);
		} else {
			throw new SuperheroNotFoundException(String.format("Superhero (id=%d) not found.", id));
		}
	}

	@DeleteMapping("/superheroes/{id}")
	@Operation(summary = "Deletes a superheroes by id", tags = "Superhero")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "deleted superhero",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Superhero.class)) }),
			@ApiResponse(responseCode = "400", description = "bad request",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Error.class)) }),
			@ApiResponse(responseCode = "404", description = "Superhero not found",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Error.class)) }),
			@ApiResponse(responseCode = "500", description = "Internal Server error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)) })

	})
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {
		superheroService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/superheroes")
	@Operation(summary = "Deletes all superheroes", tags = "Superhero")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "deleted superheroes",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Superhero.class)) }),
			@ApiResponse(responseCode = "500", description = "Internal Server error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)) })
	})
	public ResponseEntity<HttpStatus> deleteAll() {
		superheroService.deleteAll();
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/superheroes/search")
	public ResponseEntity<List<Superhero>> findByName(@RequestParam(defaultValue = "") String name) {
		final List<Superhero> superheroes = superheroService.findByName(name);
		return new ResponseEntity<>(superheroes, HttpStatus.OK);
	}
}

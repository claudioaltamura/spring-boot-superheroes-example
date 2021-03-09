package de.claudioaltamura.superheroes.controller;

import de.claudioaltamura.superheroes.entity.SuperHero;
import de.claudioaltamura.superheroes.repository.SuperHeroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
public class SuperHeroController {
    private final SuperHeroRepository superHeroRepository;

    public SuperHeroController(SuperHeroRepository superHeroRepository) {
        this.superHeroRepository = superHeroRepository;
    }

    @GetMapping(value = "/superheroes", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<SuperHero> getSuperHeroes() {
        return superHeroRepository.findAll();
    }

    @GetMapping(value = "/superheroes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SuperHero getSuperHeroes(@PathVariable long id){
        return superHeroRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Invalid super hero id %s", id)));
    }

    @PostMapping(value = "/superheroes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SuperHero createRide(@Valid @RequestBody SuperHero superHero) {
        return superHeroRepository.save(superHero);
    }
}

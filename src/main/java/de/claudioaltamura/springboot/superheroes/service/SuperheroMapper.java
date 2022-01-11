package de.claudioaltamura.springboot.superheroes.service;

import org.springframework.stereotype.Component;

import de.claudioaltamura.springboot.superheroes.Superhero;
import de.claudioaltamura.springboot.superheroes.entity.SuperheroEntity;

@Component
public class SuperheroMapper {

	public Superhero toDto(SuperheroEntity superheroEntity) {
		Superhero superhero = new Superhero();
		superhero.setId(superheroEntity.getId());
		superhero.setName(superheroEntity.getName());
		superhero.setPower(superheroEntity.getPower());
		superhero.setRealName(superheroEntity.getRealName());
		return superhero;
	}

}

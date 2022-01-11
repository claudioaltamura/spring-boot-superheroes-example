package de.claudioaltamura.springboot.superheroes;

public class SuperheroNotFoundException extends RuntimeException {

	public SuperheroNotFoundException(String message) {
		super(message);
	}

}

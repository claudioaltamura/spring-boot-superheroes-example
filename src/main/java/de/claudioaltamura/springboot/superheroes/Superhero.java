package de.claudioaltamura.springboot.superheroes;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class Superhero {

	private Long id;

	@NotEmpty
	private String name;

	private String realName;

	@DecimalMin(value="0.0")
	@DecimalMax(value="100.0")
	private double power;

	public Superhero(){}

	public Superhero(String name, String realName, double power) {
		this.name = name;
		this.realName = realName;
		this.power = power;
	}
}

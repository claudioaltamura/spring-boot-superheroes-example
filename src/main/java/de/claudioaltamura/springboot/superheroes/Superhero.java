package de.claudioaltamura.springboot.superheroes;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Superhero {

	private Long id;

	@NotEmpty
	private String name;

	private String realName;

	@DecimalMin(value="0.0")
	@DecimalMax(value="100.0")
	private double power;

	public Superhero(){}

	public Superhero(long id, String name, String realName, double power) {
		this.id = id;
		this.name = name;
		this.realName = realName;
		this.power = power;
	}

	public Superhero(String name, String realName, double power) {
		this.name = name;
		this.realName = realName;
		this.power = power;
	}
}

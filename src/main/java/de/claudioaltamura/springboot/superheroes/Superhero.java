package de.claudioaltamura.springboot.superheroes;

import java.util.Objects;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;

public class Superhero {

	private Long id;

	@NotEmpty
	private String name;

	private String realName;

	@DecimalMin(value="0.0")
	@DecimalMax(value="100.0")
	private double power;

	public Superhero() {}

	public Superhero(String name, String realName, double power) {
		this.name = name;
		this.realName = realName;
		this.power = power;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public double getPower() {
		return power;
	}

	public void setPower(double power) {
		this.power = power;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Superhero superhero = (Superhero) o;
		return Double.compare(superhero.power, power) == 0 && Objects.equals(id, superhero.id) && Objects.equals(name, superhero.name) && Objects.equals(realName, superhero.realName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, realName, power);
	}

	@Override
	public String toString() {
		return "Superhero{" +
				"id=" + id +
				", name='" + name + '\'' +
				", realName='" + realName + '\'' +
				", power=" + power +
				'}';
	}
}

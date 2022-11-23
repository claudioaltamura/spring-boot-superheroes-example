package de.claudioaltamura.springboot.superheroes.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Setter
@Table(name="superheroes")
public class SuperheroEntity {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Column(name = "name")
	private String name;

	@Column(name = "real_name")
	private String realName;

	@DecimalMin(value="0.0")
	@DecimalMax(value="100.0")
	@Column(name = "power", nullable = false)
	private double power;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SuperheroEntity superhero = (SuperheroEntity) o;
		return Objects.equals(id, superhero.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
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

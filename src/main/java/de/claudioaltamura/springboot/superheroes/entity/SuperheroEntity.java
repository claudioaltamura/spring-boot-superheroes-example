package de.claudioaltamura.springboot.superheroes.entity;

import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Table(name="superheroes")
public class SuperheroEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include private Long id;

	@NotEmpty
	@Column(name = "name")
	private String name;

	@Column(name = "real_name")
	private String realName;

	@DecimalMin(value = "0.0")
	@DecimalMax(value = "100.0")
	@Column(name = "power", nullable = false)
	private double power;

}


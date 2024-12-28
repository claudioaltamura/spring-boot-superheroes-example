package de.claudioaltamura.springboot.superheroes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import de.claudioaltamura.springboot.superheroes.entity.SuperheroEntity;
import org.springframework.data.jpa.repository.Query;

public interface SuperheroRepository extends JpaRepository<SuperheroEntity, Long> {

	@Query(value = "SELECT p FROM SuperheroEntity p ")
	Page<SuperheroEntity> findAll(Pageable pageable);

	List<SuperheroEntity> findByName(String name);
}

package de.claudioaltamura.superheroes.repository;

import de.claudioaltamura.superheroes.entity.SuperHero;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuperHeroRepository extends CrudRepository<SuperHero, Long> {
    List<SuperHero> findByName(String name);
}

package de.claudioaltamura.superheroes;

import de.claudioaltamura.superheroes.entity.SuperHero;
import de.claudioaltamura.superheroes.repository.SuperHeroRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SuperHeroesApplication  {
    public static void main(String[] args) {
        SpringApplication.run(SuperHeroesApplication.class);
    }

    @Bean
    public CommandLineRunner sampleData(SuperHeroRepository repository) {
        return (args) -> {
            repository.save(new SuperHero("Batman", "Bruce Wayne"));
            repository.save(new SuperHero("Superman", "Clark Gable"));
        };
    }
}

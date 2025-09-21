package hu.hm.fitjourneyapi.repository.fitness;

import hu.hm.fitjourneyapi.model.fitness.Excercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExcerciseRepository extends JpaRepository<Excercise, Integer> {
    Optional<Object> findByName(String name);
}
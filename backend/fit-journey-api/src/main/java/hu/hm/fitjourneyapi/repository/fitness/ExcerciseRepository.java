package hu.hm.fitjourneyapi.repository.fitness;

import hu.hm.fitjourneyapi.model.fitness.Excercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExcerciseRepository extends JpaRepository<Excercise, Integer> {
}
package hu.hm.fitjourneyapi.repository.fitness;

import hu.hm.fitjourneyapi.model.fitness.DefaultExercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DefaultExercisesRepository extends JpaRepository<DefaultExercise, Long> {

    List<DefaultExercise> findAllByNameContainingIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
}

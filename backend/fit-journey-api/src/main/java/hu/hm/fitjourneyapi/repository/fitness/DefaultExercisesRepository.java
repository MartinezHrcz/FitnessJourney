package hu.hm.fitjourneyapi.repository.fitness;

import hu.hm.fitjourneyapi.model.fitness.DefaultExercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DefaultExercisesRepository extends JpaRepository<DefaultExercise, UUID> {

    List<DefaultExercise> findAllByNameContainingIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
}

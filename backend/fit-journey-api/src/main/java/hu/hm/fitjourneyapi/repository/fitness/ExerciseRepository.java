package hu.hm.fitjourneyapi.repository.fitness;

import hu.hm.fitjourneyapi.model.fitness.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExerciseRepository extends JpaRepository<Exercise, UUID> {
    Optional<Object> findByName(String name);

    List<Exercise> getExercisesByName(String name);

    List<Exercise> getAllById(UUID id);

    List<Exercise> getExerciseById(UUID id);
}
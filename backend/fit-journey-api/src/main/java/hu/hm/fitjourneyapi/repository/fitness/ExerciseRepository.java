package hu.hm.fitjourneyapi.repository.fitness;

import hu.hm.fitjourneyapi.model.fitness.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Optional<Object> findByName(String name);

    List<Exercise> getExercisesByName(String name);

    List<Exercise> getAllById(long id);

    List<Exercise> getExerciseById(long id);
}
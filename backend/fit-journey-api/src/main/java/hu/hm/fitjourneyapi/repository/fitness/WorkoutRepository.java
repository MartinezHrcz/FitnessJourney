package hu.hm.fitjourneyapi.repository.fitness;

import hu.hm.fitjourneyapi.model.fitness.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    Workout findByName(String placeholderWorkout);

    List<Workout> findWorkoutsByUser_Id(UUID userId);
}
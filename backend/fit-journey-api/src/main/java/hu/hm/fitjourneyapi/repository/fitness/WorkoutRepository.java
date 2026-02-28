package hu.hm.fitjourneyapi.repository.fitness;

import hu.hm.fitjourneyapi.model.fitness.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkoutRepository extends JpaRepository<Workout, UUID> {

    Workout findByName(String placeholderWorkout);

    @Query("SELECT w FROM Workout w LEFT JOIN FETCH w.exercises WHERE w.user.id = :userId")
    List<Workout> findWorkoutsByUser_Id(@Param("userId") UUID userId);

    @Query("SELECT w FROM Workout w LEFT JOIN FETCH w.exercises WHERE w.id = :id")
    Optional<Workout> findByIdWithExercises(@Param("id") UUID id);
}
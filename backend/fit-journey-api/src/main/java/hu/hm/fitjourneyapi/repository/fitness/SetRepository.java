package hu.hm.fitjourneyapi.repository.fitness;

import hu.hm.fitjourneyapi.model.fitness.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SetRepository extends JpaRepository<Set, Long> {
    @Query("SELECT s FROM Set s JOIN FETCH s.exercise")
    List<Set> findAllWithExercises();
}
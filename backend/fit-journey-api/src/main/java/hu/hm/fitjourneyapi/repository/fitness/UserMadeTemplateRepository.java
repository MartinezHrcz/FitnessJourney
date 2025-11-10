package hu.hm.fitjourneyapi.repository.fitness;

import hu.hm.fitjourneyapi.model.fitness.DefaultExercise;
import hu.hm.fitjourneyapi.model.fitness.UserMadeTemplates;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserMadeTemplateRepository extends JpaRepository<UserMadeTemplates, Long> {
    List<UserMadeTemplates> findAllByNameContainingIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
}

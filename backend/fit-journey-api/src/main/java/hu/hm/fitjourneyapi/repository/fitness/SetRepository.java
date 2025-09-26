package hu.hm.fitjourneyapi.repository.fitness;

import hu.hm.fitjourneyapi.model.fitness.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SetRepository extends JpaRepository<Set, Long> {
    Set getSetById(long id);
}
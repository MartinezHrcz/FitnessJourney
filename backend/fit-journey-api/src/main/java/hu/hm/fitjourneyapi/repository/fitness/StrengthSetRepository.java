package hu.hm.fitjourneyapi.repository.fitness;

import hu.hm.fitjourneyapi.model.fitness.StrengthSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StrengthSetRepository extends JpaRepository<StrengthSet, Long> {
    
}

package hu.hm.fitjourneyapi.repository.diet;

import hu.hm.fitjourneyapi.model.diet.CalorieLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CalorieLogRepository extends JpaRepository<hu.hm.fitjourneyapi.model.diet.CalorieLog, UUID> {
    Optional<CalorieLog> findByUserIdAndDate(UUID userId, LocalDate date);

    List<CalorieLog> findAllByUserId(UUID userId);
}

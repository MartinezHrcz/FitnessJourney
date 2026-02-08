package hu.hm.fitjourneyapi.repository.diet;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MealEntryRepository extends JpaRepository<hu.hm.fitjourneyapi.model.diet.MealEntry, UUID> {
}

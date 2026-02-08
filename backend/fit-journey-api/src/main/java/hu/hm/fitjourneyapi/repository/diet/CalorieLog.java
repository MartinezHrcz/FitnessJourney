package hu.hm.fitjourneyapi.repository.diet;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface CalorieLog extends JpaRepository<hu.hm.fitjourneyapi.model.diet.CalorieLog, UUID> {
}

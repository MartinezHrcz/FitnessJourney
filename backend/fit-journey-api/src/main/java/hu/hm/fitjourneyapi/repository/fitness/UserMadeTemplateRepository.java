package hu.hm.fitjourneyapi.repository.fitness;

import hu.hm.fitjourneyapi.model.fitness.UserMadeTemplates;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface UserMadeTemplateRepository extends JpaRepository<UserMadeTemplates, UUID> {
}

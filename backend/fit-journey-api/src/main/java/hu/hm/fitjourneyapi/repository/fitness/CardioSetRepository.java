package hu.hm.fitjourneyapi.repository.fitness;

import hu.hm.fitjourneyapi.model.fitness.CardioSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardioSetRepository extends JpaRepository<CardioSet, Integer> {
}
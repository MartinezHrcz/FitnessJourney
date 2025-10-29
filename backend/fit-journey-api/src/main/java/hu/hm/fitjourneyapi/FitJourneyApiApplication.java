package hu.hm.fitjourneyapi;

import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutDTO;
import hu.hm.fitjourneyapi.mapper.fitness.WorkoutMapper;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.fitness.Workout;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootApplication
@EnableJpaAuditing
@EnableMethodSecurity
public class FitJourneyApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FitJourneyApiApplication.class, args);
    }

}
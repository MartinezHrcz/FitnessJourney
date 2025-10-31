package hu.hm.fitjourneyapi.services.initialization;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.hm.fitjourneyapi.model.enums.ExerciseTypes;
import hu.hm.fitjourneyapi.model.enums.WeightType;
import hu.hm.fitjourneyapi.model.fitness.Exercise;
import hu.hm.fitjourneyapi.repository.fitness.ExerciseRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class ExerciseInitializer {
    private final ExerciseRepository exerciseRepository;
    private final ObjectMapper objectMapper;

    public ExerciseInitializer(ExerciseRepository exerciseRepository, ObjectMapper objectMapper) {
        this.exerciseRepository = exerciseRepository;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init(){
        log.info("Exercise initialization started");
        try(InputStream is = ExerciseInitializer.class.getClassLoader().getResourceAsStream("defaultExercises.json")){
            JsonNode defaultExercises = objectMapper.readTree(is).get("exercises");
            for(JsonNode exercise: defaultExercises){
                String name = exercise.get("name").asText();
                if (!exerciseRepository.getExercisesByName(name).isEmpty()) continue;
                Exercise initialExercise = Exercise.builder()
                        .name(name)
                        .description(exercise.get("description").asText())
                        .weightType(WeightType.valueOf(exercise.get("weightType").asText()))
                        .type(ExerciseTypes.valueOf(exercise.get("exerciseType").asText()))
                        .build();
                exerciseRepository.save(initialExercise);
            }
        } catch (IOException e) {
            log.error("Exercise initialization failed", e);
            e.printStackTrace();
        }
    }
}

package hu.hm.fitjourneyapi.dto.fitness.exerciseTemplates;

import hu.hm.fitjourneyapi.model.enums.ExerciseTypes;
import hu.hm.fitjourneyapi.model.enums.WeightType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Getter
@Setter
public class UserMadeExercisesDTO {
    long id;
    String name;
    String description;
    WeightType weightType;
    ExerciseTypes type;
    UUID userId;
}

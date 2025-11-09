package hu.hm.fitjourneyapi.dto.fitness.premadeExercises;

import hu.hm.fitjourneyapi.model.enums.ExerciseTypes;
import hu.hm.fitjourneyapi.model.enums.WeightType;

public class DefaultExerciseDTO {
    long id;
    String name;
    String description;
    WeightType weightType;
    ExerciseTypes type;
}

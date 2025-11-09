package hu.hm.fitjourneyapi.dto.fitness.premadeExercises;

import hu.hm.fitjourneyapi.model.enums.ExerciseTypes;
import hu.hm.fitjourneyapi.model.enums.WeightType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class DefaultExerciseDTO {
    long id;
    String name;
    String description;
    WeightType weightType;
    ExerciseTypes type;
}

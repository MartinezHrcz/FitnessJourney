package hu.hm.fitjourneyapi.dto.fitness.excercise;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import hu.hm.fitjourneyapi.model.enums.ExerciseTypes;
import hu.hm.fitjourneyapi.model.enums.WeightType;
import io.swagger.v3.core.util.Json;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@SuperBuilder
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ExerciseStrengthSetDTO.class, names={"RESISTANCE", "NOT_GIVEN", "BODY_WEIGHT"}),
        @JsonSubTypes.Type(value = ExerciseCardioSetDTO.class, name="CARDIO"),
        @JsonSubTypes.Type(value = ExerciseFlexibilitySetDTO.class, name="FLEXIBILITY")
})
public abstract class AbstractExerciseDTO {
    private UUID id;
    private String name;
    private String description;
    private UUID workoutId;
    private WeightType weightType;
    private ExerciseTypes type;
}

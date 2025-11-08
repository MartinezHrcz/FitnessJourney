package hu.hm.fitjourneyapi.dto.fitness.set;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import hu.hm.fitjourneyapi.dto.fitness.excercise.ExerciseCardioSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.excercise.ExerciseFlexibilitySetDTO;
import hu.hm.fitjourneyapi.dto.fitness.excercise.ExerciseStrengthSetDTO;
import hu.hm.fitjourneyapi.model.enums.SetType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
        @JsonSubTypes.Type(value = StrengthSetDTO.class, names={"STRENGTH"}),
        @JsonSubTypes.Type(value = CardioSetDTO.class, name="CARDIO"),
        @JsonSubTypes.Type(value = FlexibilitySetDTO.class, name="FLEXIBILITY")
})
public abstract class AbstractSetDTO {
    private long id;
    private long exerciseId;
    public abstract SetType getType();
}

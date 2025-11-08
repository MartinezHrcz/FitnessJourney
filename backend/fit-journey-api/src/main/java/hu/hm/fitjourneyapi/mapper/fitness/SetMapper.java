package hu.hm.fitjourneyapi.mapper.fitness;

import hu.hm.fitjourneyapi.dto.fitness.set.AbstractSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.CardioSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.FlexibilitySetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.StrengthSetDTO;
import hu.hm.fitjourneyapi.exception.fitness.setExceptions.InvalidSetType;
import hu.hm.fitjourneyapi.mapper.fitness.set.SetMapperInternal;
import hu.hm.fitjourneyapi.model.fitness.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SetMapper {

    private final SetMapperInternal internal;

    public <T extends AbstractSetDTO> Set toEntity(T dto, Exercise exercise){
        if (dto instanceof StrengthSetDTO s) { return internal.toStrengthSet(s, exercise); }
        if (dto instanceof CardioSetDTO s) { return internal.toCardioSet(s, exercise); }
        if (dto instanceof FlexibilitySetDTO s) { return internal.toFlexibilitySet(s, exercise); }

        throw new InvalidSetType("Unsupported set type: " + dto.getClass().getName());
    }

    public <S extends Set> AbstractSetDTO toDto(S set, Exercise exercise){
        if (set instanceof StrengthSet s) {return internal.toStrengthSetDTO(s);}
        if (set instanceof CardioSet s) {return internal.toCardioSetDTO(s);}
        if (set instanceof FlexibilitySet s) {return internal.toFlexibilitySetDTO(s);}

        throw new InvalidSetType("Unsupported set type: " + set.getClass().getName());
    }

}

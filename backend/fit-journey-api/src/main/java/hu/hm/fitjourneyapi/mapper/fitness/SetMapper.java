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
        return switch (dto) {
            case StrengthSetDTO s    -> internal.toStrengthSet(s, exercise);
            case CardioSetDTO s      -> internal.toCardioSet(s, exercise);
            case FlexibilitySetDTO s -> internal.toFlexibilitySet(s, exercise);
            default -> throw new InvalidSetType("Unsupported DTO type: " + dto.getClass().getName());
        };
    }

    public <S extends Set> AbstractSetDTO toDto(S set, Exercise exercise){
        return switch (set) {
            case StrengthSet s    -> internal.toStrengthSetDTO(s);
            case CardioSet s      -> internal.toCardioSetDTO(s);
            case FlexibilitySet s -> internal.toFlexibilitySetDTO(s);
            default -> throw new InvalidSetType("Unsupported Entity type: " + set.getClass().getName());
        };
    }

    public void updateEntity(AbstractSetDTO dto, Set existing){
        switch (dto) {
            case StrengthSetDTO s when existing instanceof StrengthSet e ->
                    internal.updateStrengthSet(s, e);
            case CardioSetDTO s when existing instanceof CardioSet e ->
                    internal.updateCardioSet(s, e);
            case FlexibilitySetDTO s when existing instanceof FlexibilitySet e ->
                    internal.updateFlexibilitySet(s, e);
            default ->
                    throw new InvalidSetType("Mismatch between DTO and Entity types");
        }
    }

}

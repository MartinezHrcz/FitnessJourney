package hu.hm.fitjourneyapi.services.interfaces.fitness;

import hu.hm.fitjourneyapi.dto.fitness.set.AbstractSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.CardioSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.FlexibilitySetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.StrengthSetDTO;

import java.util.List;

public interface SetService {

    AbstractSetDTO getSetBySetId(long id);
    List<AbstractSetDTO> getSets();

    AbstractSetDTO createSet(AbstractSetDTO abstractSetDTO);

    StrengthSetDTO createStrengthSet(StrengthSetDTO strengthSetDTO);
    FlexibilitySetDTO createFlexibilitySet(FlexibilitySetDTO flexibilitySet);
    CardioSetDTO createCardioSet(CardioSetDTO cardioSet);

    AbstractSetDTO updateSet(long id,AbstractSetDTO abstractSetDTO);

    StrengthSetDTO updateStrengthSet(StrengthSetDTO strengthSetDTO);
    FlexibilitySetDTO updateFlexibilitySet(FlexibilitySetDTO flexibilitySet);
    CardioSetDTO updateCardioSet(CardioSetDTO cardioSet);

    void deleteSetById(long id);
}

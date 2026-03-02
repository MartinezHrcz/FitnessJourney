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

    AbstractSetDTO updateSet(long id,AbstractSetDTO abstractSetDTO);

    void deleteSetById(long id);
}

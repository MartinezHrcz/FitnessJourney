package hu.hm.fitjourneyapi.services.interfaces.fitness;

import hu.hm.fitjourneyapi.dto.fitness.exerciseTemplates.DefaultExerciseDTO;

import java.util.List;
import java.util.UUID;

public interface DefaultExerciseService {

    List<DefaultExerciseDTO> getDefaultExercises();

    DefaultExerciseDTO getDefaultExercise(UUID id);

    List<DefaultExerciseDTO> getDefaultExercisesByName(String name);

}

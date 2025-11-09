package hu.hm.fitjourneyapi.services.interfaces.fitness;

import hu.hm.fitjourneyapi.dto.fitness.exerciseTemplates.DefaultExerciseDTO;

import java.util.List;

public interface DefaultExerciseService {

    List<DefaultExerciseDTO> getDefaultExercises();

    DefaultExerciseDTO getDefaultExercise(long id);

    List<DefaultExerciseDTO> getDefaultExercisesByName(String name);

}

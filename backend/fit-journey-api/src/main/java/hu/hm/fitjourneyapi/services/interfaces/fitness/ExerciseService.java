package hu.hm.fitjourneyapi.services.interfaces.fitness;

import hu.hm.fitjourneyapi.dto.fitness.excercise.*;
import hu.hm.fitjourneyapi.dto.fitness.set.AbstractSetDTO;
import hu.hm.fitjourneyapi.model.enums.ExerciseTypes;
import hu.hm.fitjourneyapi.model.fitness.Exercise;

import java.util.List;
import java.util.UUID;

public interface ExerciseService {
    List<AbstractExerciseDTO> getExercises();

    List<AbstractExerciseDTO> getByWorkoutId(UUID id);

    List<AbstractExerciseDTO> getByUserId(UUID id);

    List<AbstractExerciseDTO> getByName(String name);

    AbstractExerciseDTO getById(UUID id);

    AbstractExerciseDTO createExercise(AbstractExerciseDTO dto) throws NoSuchFieldException;

    AbstractExerciseDTO updateExercise(UUID id, ExerciseUpdateDTO dto);

    AbstractExerciseDTO addSetById(UUID id, AbstractSetDTO abstractSetDTO);

    AbstractExerciseDTO removeSetById(UUID id, long setId);

    void deleteExerciseById(UUID id);
}

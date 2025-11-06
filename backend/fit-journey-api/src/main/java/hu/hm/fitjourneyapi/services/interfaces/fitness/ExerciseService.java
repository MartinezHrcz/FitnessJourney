package hu.hm.fitjourneyapi.services.interfaces.fitness;

import hu.hm.fitjourneyapi.dto.fitness.excercise.*;
import hu.hm.fitjourneyapi.model.enums.ExerciseTypes;
import hu.hm.fitjourneyapi.model.fitness.Exercise;

import java.util.List;
import java.util.UUID;

public interface ExerciseService {
    List<AbstractExerciseDTO> getExercises();

    List<AbstractExerciseDTO> getByWorkoutId(long id);

    List<AbstractExerciseDTO> getByUserId(UUID id);

    List<AbstractExerciseDTO> getByName(String name);

    AbstractExerciseDTO getById(long id);

    AbstractExerciseDTO createExercise(AbstractExerciseDTO dto) throws NoSuchFieldException;

    @Deprecated(forRemoval = true)
    ExerciseStrengthSetDTO createExerciseStrengthSet(ExerciseStrengthSetDTO exerciseStrengthSetDTO);

    @Deprecated(forRemoval = true)
    ExerciseFlexibilitySetDTO createExerciseFlexibilitySet(ExerciseFlexibilitySetDTO flexibilitySetDTO);

    @Deprecated(forRemoval = true)
    ExerciseCardioSetDTO createExerciseCardioSet(ExerciseCardioSetDTO cardioSetDTO);

    AbstractExerciseDTO updateExercise(long id, ExerciseUpdateDTO dto);

    void deleteExerciseById(long id);
}

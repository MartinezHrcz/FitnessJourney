package hu.hm.fitjourneyapi.services.interfaces.fitness;

import hu.hm.fitjourneyapi.dto.fitness.excercise.AbstractExerciseDTO;
import hu.hm.fitjourneyapi.dto.fitness.excercise.ExerciseCardioSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.excercise.ExerciseFlexibilitySetDTO;
import hu.hm.fitjourneyapi.dto.fitness.excercise.ExerciseStrengthSetDTO;

import java.util.List;

public interface ExerciseService {
    List<AbstractExerciseDTO> getExercises();

    List<AbstractExerciseDTO> getByWorkoutId(long id);

    List<AbstractExerciseDTO> getByUserId(long id);

    List<AbstractExerciseDTO> getByName(String name);

    AbstractExerciseDTO getById(long id);

    ExerciseStrengthSetDTO createExerciseStrengthSet(ExerciseStrengthSetDTO exerciseStrengthSetDTO);

    ExerciseFlexibilitySetDTO createExerciseFlexibilitySet(ExerciseFlexibilitySetDTO flexibilitySetDTO);

    ExerciseCardioSetDTO createExerciseCardioSet(ExerciseCardioSetDTO cardioSetDTO);

    ExerciseStrengthSetDTO updateExerciseStrengthSet(ExerciseStrengthSetDTO dto);

    ExerciseFlexibilitySetDTO updateExerciseFlexibilitySet(ExerciseFlexibilitySetDTO dto);

    ExerciseCardioSetDTO updateExerciseCardioSet(ExerciseCardioSetDTO dto);

    void deleteExerciseById(long id);
}

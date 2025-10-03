package hu.hm.fitjourneyapi.services.implementation.fitness;


import hu.hm.fitjourneyapi.dto.fitness.excercise.AbstractExerciseDTO;
import hu.hm.fitjourneyapi.dto.fitness.excercise.ExerciseCardioSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.excercise.ExerciseFlexibilitySetDTO;
import hu.hm.fitjourneyapi.dto.fitness.excercise.ExerciseStrengthSetDTO;
import hu.hm.fitjourneyapi.mapper.fitness.ExerciseMapper;
import hu.hm.fitjourneyapi.model.fitness.Exercise;
import hu.hm.fitjourneyapi.repository.fitness.ExerciseRepository;
import hu.hm.fitjourneyapi.repository.fitness.WorkoutRepository;
import hu.hm.fitjourneyapi.services.interfaces.fitness.ExerciseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final WorkoutRepository workoutRepository;
    private final ExerciseMapper exerciseMapper;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository, WorkoutRepository workoutRepository, ExerciseMapper exerciseMapper) {
        this.exerciseRepository = exerciseRepository;
        this.workoutRepository = workoutRepository;
        this.exerciseMapper = exerciseMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public List<AbstractExerciseDTO> getExercises() {
        List<Exercise> exercises = exerciseRepository.findAll();
        List<AbstractExerciseDTO> exerciseDTOs = exerciseMapper.toExerciseDTOs(exercises);
        return exerciseDTOs;
    }

    @Override
    public List<AbstractExerciseDTO> getByWorkoutId(long id) {
        return List.of();
    }

    @Override
    public List<AbstractExerciseDTO> getByUserId(long id) {
        return List.of();
    }

    @Override
    public List<AbstractExerciseDTO> getByName(String name) {
        return List.of();
    }

    @Override
    public AbstractExerciseDTO getById(long id) {
        return null;
    }

    @Override
    public ExerciseStrengthSetDTO createExerciseStrengthSet(ExerciseStrengthSetDTO exerciseStrengthSetDTO) {
        return null;
    }

    @Override
    public ExerciseFlexibilitySetDTO createExerciseFlexibilitySet(ExerciseFlexibilitySetDTO flexibilitySetDTO) {
        return null;
    }

    @Override
    public ExerciseCardioSetDTO createExerciseCardioSet(ExerciseCardioSetDTO cardioSetDTO) {
        return null;
    }

    @Override
    public ExerciseStrengthSetDTO updateExerciseStrengthSet(ExerciseStrengthSetDTO dto) {
        return null;
    }

    @Override
    public ExerciseFlexibilitySetDTO updateExerciseFlexibilitySet(ExerciseFlexibilitySetDTO dto) {
        return null;
    }

    @Override
    public ExerciseCardioSetDTO updateExerciseCardioSet(ExerciseCardioSetDTO dto) {
        return null;
    }

    @Override
    public void deleteExerciseById(long id) {

    }
}

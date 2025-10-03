package hu.hm.fitjourneyapi.services.implementation.fitness;


import hu.hm.fitjourneyapi.dto.fitness.excercise.AbstractExerciseDTO;
import hu.hm.fitjourneyapi.dto.fitness.excercise.ExerciseCardioSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.excercise.ExerciseFlexibilitySetDTO;
import hu.hm.fitjourneyapi.dto.fitness.excercise.ExerciseStrengthSetDTO;
import hu.hm.fitjourneyapi.exception.fitness.ExerciseNotFound;
import hu.hm.fitjourneyapi.mapper.fitness.ExerciseMapper;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.fitness.Exercise;
import hu.hm.fitjourneyapi.model.fitness.Workout;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.fitness.ExerciseRepository;
import hu.hm.fitjourneyapi.repository.fitness.WorkoutRepository;
import hu.hm.fitjourneyapi.services.interfaces.fitness.ExerciseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final WorkoutRepository workoutRepository;
    private final ExerciseMapper exerciseMapper;
    private final UserRepository userRepository;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository, WorkoutRepository workoutRepository, ExerciseMapper exerciseMapper, UserRepository userRepository) {
        this.exerciseRepository = exerciseRepository;
        this.workoutRepository = workoutRepository;
        this.exerciseMapper = exerciseMapper;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<AbstractExerciseDTO> getExercises() {
        log.debug("Fetching all exercises");
        List<Exercise> exercises = exerciseRepository.findAll();
        List<AbstractExerciseDTO> exerciseDTOs = exerciseMapper.toExerciseDTOs(exercises);
        log.debug("Fetched all exercises");
        return exerciseDTOs;
    }

    @Transactional(readOnly = true)
    @Override
    public List<AbstractExerciseDTO> getByWorkoutId(long id) {
        log.debug("Fetching exercise by workout id {}", id);
        Workout workout = workoutRepository.getReferenceById(id);
        List<Exercise> exercises = workout.getExercises();
        List<AbstractExerciseDTO> exerciseDTOs = exerciseMapper.toExerciseDTOs(exercises);
        log.debug("Fetched exercise by workout id {}", id);
        return exerciseDTOs;
    }

    @Transactional(readOnly = true)
    @Override
    public List<AbstractExerciseDTO> getByUserId(long id) {
        log.debug("Fetching exercise by workout id {}", id);
        User user = userRepository.getReferenceById(id);
        List<Workout> workouts = user.getWorkouts();
        List<Exercise> exercises = new ArrayList<>();
        workouts.forEach(workout -> exercises.addAll(workout.getExercises()));
        List<AbstractExerciseDTO> exerciseDTOs = exerciseMapper.toExerciseDTOs(exercises);
        log.debug("Fetched exercise by user id {}", id);
        return exerciseDTOs;
    }

    @Transactional(readOnly = true)
    @Override
    public List<AbstractExerciseDTO> getByName(String name) {
        log.debug("Fetching all exercises by name {}", name);
        List<Exercise> exercises = exerciseRepository.getExercisesByName(name);
        List<AbstractExerciseDTO> exerciseDTOs = exerciseMapper.toExerciseDTOs(exercises);
        log.debug("Fetched all exercises by name {}", name);
        return exerciseDTOs;
    }

    @Transactional(readOnly = true)
    @Override
    public AbstractExerciseDTO getById(long id) {
        log.debug("Fetching exercise by id {}", id);
        Exercise exercise = exerciseRepository.findById(id).orElseThrow(
                ()-> new ExerciseNotFound("Exercise not found by id"));
        return switch (exercise.getType()) {
            case BODYWEIGHT, RESISTANCE, NOT_GIVEN -> exerciseMapper.toExerciseStrengthSetDTO(exercise);
            case FLEXIBILITY -> exerciseMapper.toExerciseFlexibilitySetDTO(exercise);
            case CARDIO -> exerciseMapper.toExerciseCardioSetDTO(exercise);
        };
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

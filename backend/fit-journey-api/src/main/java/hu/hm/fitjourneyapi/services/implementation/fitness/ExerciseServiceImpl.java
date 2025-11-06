package hu.hm.fitjourneyapi.services.implementation.fitness;


import hu.hm.fitjourneyapi.dto.fitness.excercise.AbstractExerciseDTO;
import hu.hm.fitjourneyapi.dto.fitness.excercise.ExerciseCardioSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.excercise.ExerciseFlexibilitySetDTO;
import hu.hm.fitjourneyapi.dto.fitness.excercise.ExerciseStrengthSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.CardioSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.FlexibilitySetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.StrengthSetDTO;
import hu.hm.fitjourneyapi.exception.fitness.ExerciseNotFound;
import hu.hm.fitjourneyapi.exception.fitness.WorkoutNotFound;
import hu.hm.fitjourneyapi.mapper.fitness.ExerciseMapper;
import hu.hm.fitjourneyapi.mapper.fitness.SetMapper;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.enums.ExerciseTypes;
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
import java.util.UUID;

@Slf4j
@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final WorkoutRepository workoutRepository;
    private final ExerciseMapper exerciseMapper;
    private final SetMapper setMapper;
    private final UserRepository userRepository;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository, WorkoutRepository workoutRepository, ExerciseMapper exerciseMapper, SetMapper setMapper, UserRepository userRepository) {
        this.exerciseRepository = exerciseRepository;
        this.workoutRepository = workoutRepository;
        this.exerciseMapper = exerciseMapper;
        this.setMapper = setMapper;
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
    public List<AbstractExerciseDTO> getByUserId(UUID id) {
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
            case BODY_WEIGHT, RESISTANCE, NOT_GIVEN -> exerciseMapper.toExerciseStrengthSetDTO(exercise);
            case FLEXIBILITY -> exerciseMapper.toExerciseFlexibilitySetDTO(exercise);
            case CARDIO -> exerciseMapper.toExerciseCardioSetDTO(exercise);
        };
    }

    @Override
    public AbstractExerciseDTO createExercise(AbstractExerciseDTO dto) throws NoSuchFieldException {
        Exercise result = exerciseMapper.toExercise(dto);
        result = exerciseRepository.save(result);
        return exerciseMapper.toExerciseDTO(result);
    }


    @Transactional
    @Override
    public ExerciseStrengthSetDTO createExerciseStrengthSet(ExerciseStrengthSetDTO exerciseStrengthSetDTO) {
        log.debug("Creating exercise strength set");
        Workout workout = workoutRepository.findById(exerciseStrengthSetDTO.getWorkoutId()).orElseThrow(
                ()-> new WorkoutNotFound("Workout not found by id")
        );
        Exercise exercise = exerciseMapper.toExercise(exerciseStrengthSetDTO, workout);
        exercise = exerciseRepository.save(exercise);
        log.info("Created exercise with strength set");
        return exerciseMapper.toExerciseStrengthSetDTO(exercise);
    }

    @Transactional
    @Override
    public ExerciseFlexibilitySetDTO createExerciseFlexibilitySet(ExerciseFlexibilitySetDTO flexibilitySetDTO) {
        log.debug("Creating exercise with flexibility set");
        Workout workout = workoutRepository.findById(flexibilitySetDTO.getWorkoutId()).orElseThrow(
                ()-> new WorkoutNotFound("Workout not found by id")
        );
        Exercise exercise = exerciseMapper.toExercise(flexibilitySetDTO, workout);
        exercise = exerciseRepository.save(exercise);
        log.info("Created exercise with flexibility set");
        return exerciseMapper.toExerciseFlexibilitySetDTO(exercise);
    }

    @Transactional
    @Override
    public ExerciseCardioSetDTO createExerciseCardioSet(ExerciseCardioSetDTO cardioSetDTO) {
        log.debug("Creating exercise with cardio set");
        Workout workout = workoutRepository.findById(cardioSetDTO.getWorkoutId()).orElseThrow(
                ()-> new WorkoutNotFound("Workout not found by id")
        );
        Exercise exercise = exerciseMapper.toExercise(cardioSetDTO, workout);
        exercise = exerciseRepository.save(exercise);
        log.info("Created exercise with cardio set");
        return exerciseMapper.toExerciseCardioSetDTO(exercise);
    }

    @Transactional
    @Override
    public ExerciseStrengthSetDTO updateExerciseStrengthSet(ExerciseStrengthSetDTO dto) {
        log.debug("Updating exercise strength set");
        Exercise exercise = exerciseRepository.findById(dto.getId()).orElseThrow(
                ()-> new ExerciseNotFound("Exercise not found by id")
        );

        for (StrengthSetDTO set : dto.getSets()){
            exercise.getSets().add(setMapper.toStrengthSet(set,exercise));
        }

        exercise.setName(dto.getName());
        exercise.setDescription(dto.getDescription());
        exercise.setWeightType(dto.getWeightType());
        exercise = exerciseRepository.save(exercise);
        log.info("Updated exercise strength set with name {}", dto.getName());
        return exerciseMapper.toExerciseStrengthSetDTO(exercise);
    }

    @Transactional
    @Override
    public ExerciseFlexibilitySetDTO updateExerciseFlexibilitySet(ExerciseFlexibilitySetDTO dto) {
        log.debug("Updating exercise flexibility set");
        Exercise exercise = exerciseRepository.findById(dto.getId()).orElseThrow(
                ()-> new ExerciseNotFound("Exercise not found by id")
        );

        for (FlexibilitySetDTO set : dto.getSets()){
            exercise.getSets().add(setMapper.toFlexibilitySet(set,exercise));
        }

        exercise.setName(dto.getName());
        exercise.setDescription(dto.getDescription());
        exercise = exerciseRepository.save(exercise);
        log.info("Updated exercise flexibility set with name {}", dto.getName());
        return exerciseMapper.toExerciseFlexibilitySetDTO(exercise);
    }

    @Transactional
    @Override
    public ExerciseCardioSetDTO updateExerciseCardioSet(ExerciseCardioSetDTO dto) {
        log.debug("Updating exercise cardio set");
        Exercise exercise = exerciseRepository.findById(dto.getId()).orElseThrow(
                ()-> new ExerciseNotFound("Exercise not found by id")
        );

        for (CardioSetDTO set : dto.getSets()){
            exercise.getSets().add(setMapper.toCardioSet(set,exercise));
        }

        exercise.setName(dto.getName());
        exercise.setDescription(dto.getDescription());
        exercise = exerciseRepository.save(exercise);
        log.info("Updated exercise cardio set with name {}", dto.getName());
        return exerciseMapper.toExerciseCardioSetDTO(exercise);
    }

    @Transactional
    @Override
    public void deleteExerciseById(long id) {
        log.debug("Deleting exercise with id {}", id);
        Exercise exercise = exerciseRepository.findById(id).orElseThrow(
                ()-> new ExerciseNotFound("Exercise not found by id")
        );
        log.info("Deleted exercise with id {}", id);
        exerciseRepository.delete(exercise);
    }
}

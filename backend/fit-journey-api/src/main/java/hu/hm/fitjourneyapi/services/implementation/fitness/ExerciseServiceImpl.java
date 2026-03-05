package hu.hm.fitjourneyapi.services.implementation.fitness;


import hu.hm.fitjourneyapi.dto.fitness.excercise.*;
import hu.hm.fitjourneyapi.dto.fitness.set.AbstractSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.CardioSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.FlexibilitySetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.StrengthSetDTO;
import hu.hm.fitjourneyapi.exception.fitness.ExerciseNotFound;
import hu.hm.fitjourneyapi.exception.fitness.SetNotFound;
import hu.hm.fitjourneyapi.mapper.fitness.ExerciseMapper;
import hu.hm.fitjourneyapi.mapper.fitness.SetMapper;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.fitness.*;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.fitness.ExerciseRepository;
import hu.hm.fitjourneyapi.repository.fitness.SetRepository;
import hu.hm.fitjourneyapi.repository.fitness.WorkoutRepository;
import hu.hm.fitjourneyapi.services.interfaces.fitness.ExerciseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final WorkoutRepository workoutRepository;
    private final ExerciseMapper exerciseMapper;
    private final SetMapper setMapper;
    private final UserRepository userRepository;
    private final SetRepository setRepository;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository, WorkoutRepository workoutRepository, ExerciseMapper exerciseMapper, SetMapper setMapper, UserRepository userRepository, SetRepository setRepository) {
        this.exerciseRepository = exerciseRepository;
        this.workoutRepository = workoutRepository;
        this.exerciseMapper = exerciseMapper;
        this.setMapper = setMapper;
        this.userRepository = userRepository;
        this.setRepository = setRepository;
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
    public List<AbstractExerciseDTO> getByWorkoutId(UUID id) {
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
    public AbstractExerciseDTO getById(UUID id) {
        log.debug("Fetching exercise by id {}", id);
        Exercise exercise = exerciseRepository.findById(id).orElseThrow(
                () -> new ExerciseNotFound("Exercise not found by id"));
        return exerciseMapper.toExerciseDTO(exercise);
    }

    @Transactional
    @Override
    public AbstractExerciseDTO createExercise(AbstractExerciseDTO dto) {
        Exercise result = exerciseMapper.toExercise(dto);
        result = exerciseRepository.save(result);
        return exerciseMapper.toExerciseDTO(result);
    }

    @Transactional
    @Override
    public AbstractExerciseDTO updateExercise(UUID id, ExerciseUpdateDTO dto) {
        log.debug("Updating exercise by id {}", id);
        Exercise exercise = exerciseRepository.findById(id).orElseThrow(
                () -> new ExerciseNotFound("Exercise not found by id")
        );
        exerciseMapper.updateExerciseFields(exercise, dto);
        exercise = exerciseRepository.save(exercise);
        log.info("Updated exercise by id {}", id);
        return exerciseMapper.toExerciseDTO(exercise);
    }

    @Transactional
    @Override
    public AbstractExerciseDTO addSetById(UUID id, AbstractSetDTO abstractSetDTO) {
        log.debug("Adding set to exercise by id {}", id);
        Exercise exercise = exerciseRepository.findById(id).orElseThrow(
                () -> new ExerciseNotFound("Exercise not found by id")
        );
        Set set = setMapper.toEntity(abstractSetDTO, exercise);
        exercise.addSet(set);
        exerciseRepository.save(exercise);
        log.info("Added set to exercise by id {}", id);
        return exerciseMapper.toExerciseDTO(exercise);
    }

    @Transactional
    @Override
    public AbstractExerciseDTO updateSetById(UUID id, long setId, AbstractSetDTO abstractSetDTO) {

        Exercise exercise = exerciseRepository.findById(id).orElseThrow(
                () -> new ExerciseNotFound("Exercise not found by id")
        );

        Set set = setRepository.findById(setId).orElseThrow(
                () -> new SetNotFound("Set not found by id")
        );

        if (!set.getExercise().getId().equals(id)) {
            throw new IllegalStateException("Set does not belong to the specified exercise");
        }

        setMapper.updateEntity(abstractSetDTO, set);

        setRepository.save(set);

        log.info("Updated set {} in exercise {}", setId, id);

        return exerciseMapper.toExerciseDTO(exercise);
    }

    @Transactional
    @Override
    public AbstractExerciseDTO removeSetById(UUID id, long setid) {
        log.debug("Removing set from exercise by id {}", id);
        Exercise exercise = exerciseRepository.findById(id).orElseThrow(
                () -> new ExerciseNotFound("Exercise not found by id")
        );
        Set set = setRepository.findById(setid).orElseThrow(
                () -> new SetNotFound("Set not found by id")
        );

        exercise.removeSet(set);
        setRepository.delete(set);
        exercise = exerciseRepository.save(exercise);
        log.info("Removed set from exercise by id {}", id);
        return exerciseMapper.toExerciseDTO(exercise);
    }

    @Transactional
    @Override
    public void deleteExerciseById(UUID id) {
        log.debug("Deleting exercise with id {}", id);
        Exercise exercise = exerciseRepository.findById(id).orElseThrow(
                () -> new ExerciseNotFound("Exercise not found by id")
        );
        log.info("Deleted exercise with id {}", id);
        exerciseRepository.delete(exercise);
    }
}

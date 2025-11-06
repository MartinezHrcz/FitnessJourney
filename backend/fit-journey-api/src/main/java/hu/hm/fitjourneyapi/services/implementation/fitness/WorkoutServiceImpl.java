package hu.hm.fitjourneyapi.services.implementation.fitness;

import hu.hm.fitjourneyapi.dto.fitness.excercise.AbstractExerciseDTO;
import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutCreateDTO;
import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutDTO;
import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutUpdateDTO;
import hu.hm.fitjourneyapi.exception.fitness.ExerciseNotFound;
import hu.hm.fitjourneyapi.exception.fitness.WorkoutNotFound;
import hu.hm.fitjourneyapi.exception.userExceptions.UserNotFound;
import hu.hm.fitjourneyapi.mapper.fitness.WorkoutMapper;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.fitness.Exercise;
import hu.hm.fitjourneyapi.model.fitness.Workout;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.fitness.ExerciseRepository;
import hu.hm.fitjourneyapi.repository.fitness.WorkoutRepository;
import hu.hm.fitjourneyapi.services.interfaces.fitness.WorkoutService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class WorkoutServiceImpl implements WorkoutService {

    private final UserRepository userRepository;
    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutMapper workoutMapper;

    public WorkoutServiceImpl(UserRepository userRepository, WorkoutRepository workoutRepository, ExerciseRepository exerciseRepository, WorkoutMapper workoutMapper) {
        this.userRepository = userRepository;
        this.workoutRepository = workoutRepository;
        this.exerciseRepository = exerciseRepository;
        this.workoutMapper = workoutMapper;
    }

    @Transactional
    @Override
    public WorkoutDTO createWorkout(WorkoutCreateDTO workoutCreateDTO) {
        log.debug("Attempting to create workout {}", workoutCreateDTO.getName());
        User user = userRepository.findById(workoutCreateDTO.getUserId()).orElseThrow(
                () -> {
                    log.warn("User not found for id {}", workoutCreateDTO.getUserId());
                    return new UserNotFound("User not found with id " + workoutCreateDTO.getUserId());
                }
        );
        Workout workout = workoutMapper.toWorkout(workoutCreateDTO, user);
        workout = workoutRepository.save(workout);
        user.addWorkout(workout);
        log.info("Created workout {} with id {}", workoutCreateDTO.getName(), workout.getId());
        return workoutMapper.toDTO(workout);
    }

    @Transactional(readOnly = true)
    @Override
    public WorkoutDTO getWorkoutByWorkoutId(long id) {
        log.debug("Fetching workout with id: {}", id);
        Workout workout = workoutRepository.findById(id).orElseThrow(
                () ->
                {
                    log.warn("Workout with id {} not found", id);
                    return new WorkoutNotFound("Workout not found with id " + id);
                }
        );
        log.debug("Fetched workout with id: {}", workout.getId());
        return workoutMapper.toDTO(workout);
    }

    @Transactional(readOnly = true)
    @Override
    public List<WorkoutDTO> getWorkouts() {
        log.debug("Fetching workouts");
        List<Workout> workouts = workoutRepository.findAll();
        List<WorkoutDTO> workoutDTOs = workoutMapper.toDTOList(workouts);
        log.debug("Fetched workouts");
        return workoutDTOs;
    }

    @Transactional(readOnly = true)
    @Override
    public List<WorkoutDTO> getWorkoutByUserId(UUID id) {
        log.debug("Fetching workout by user id: {}", id);
        List<Workout> workouts = workoutRepository.findWorkoutsByUser_Id(id);
        List<WorkoutDTO> workoutDTOs = workoutMapper.toDTOList(workouts);
        log.debug("Fetched workouts by user id: {}", id);
        return workoutDTOs;
    }

    @Transactional
    @Override
    public WorkoutDTO updateWorkout(long id, WorkoutUpdateDTO workoutUpdateDTO) {
        log.debug("Updating workout {}", workoutUpdateDTO.getId());
        Workout workout = workoutRepository.findById(id).orElseThrow(
                () -> {
                    log.warn("Workout with id {} not found", workoutUpdateDTO.getId());
                    return new WorkoutNotFound("Workout not found with id " + id);
                    }
                );
        workout.setName(workoutUpdateDTO.getName());
        workout.setDescription(workoutUpdateDTO.getDescription());
        workout.setLengthInMins(workoutUpdateDTO.getLengthInMins());
        User user = userRepository.findById(workoutUpdateDTO.getUserId()).orElseThrow(
                () -> {
                    log.warn("user not found with id " + workoutUpdateDTO.getUserId());
                    return new UserNotFound("User not found with id " + workoutUpdateDTO.getUserId());
                }
        );
        workout.setUser(user);
        workout = workoutRepository.save(workout);
        log.info("Updated workout {} with id {}", workoutUpdateDTO.getName(), workoutUpdateDTO.getId());
        return workoutMapper.toDTO(workout);
    }

    @Override
    public WorkoutDTO addExerciseToWorkout(long workoutId, long exerciseId) {
        Workout workout = workoutRepository.findById(workoutId).orElseThrow(
                ()->{
                    log.warn("Workout with id {} not found", workoutId);
                    return new WorkoutNotFound("Workout not found with id " + workoutId);
                }
        );
        Exercise exercise = exerciseRepository.findById(exerciseId).orElseThrow(
                () -> {
                    log.warn("Exercise with id {} not found", exerciseId);
                    return new ExerciseNotFound("Exercise not found with id " + exerciseId);
                }
        );
        exercise.setWorkout(workout);
        workout.getExercises().add(exercise);
        workout = workoutRepository.save(workout);
        exerciseRepository.save(exercise);
        log.info("Added {} to workout {} with id {}",exercise.getName(), workout.getName(), workout.getId());
        return workoutMapper.toDTO(workout);
    }

    @Transactional
    @Override
    public void deleteWorkoutById(long id) {
        log.debug("Deleting workout {}", id);
        Workout workout = workoutRepository.findById(id).orElseThrow(
                ()->{
                    log.warn("Workout with id {} not found", id);
                    return new WorkoutNotFound("Workout not found with id " + id);
                }
        );
        workoutRepository.delete(workout);
        log.info("Deleted workout {}", id);
    }
}

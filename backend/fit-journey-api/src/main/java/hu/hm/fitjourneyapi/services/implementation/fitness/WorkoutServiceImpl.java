package hu.hm.fitjourneyapi.services.implementation.fitness;

import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutCreateDTO;
import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutDTO;
import hu.hm.fitjourneyapi.exception.fitness.WorkoutNotFound;
import hu.hm.fitjourneyapi.exception.userExceptions.UserNotFound;
import hu.hm.fitjourneyapi.mapper.fitness.WorkoutMapper;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.fitness.Workout;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.fitness.WorkoutRepository;
import hu.hm.fitjourneyapi.services.interfaces.fitness.WorkoutService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class WorkoutServiceImpl implements WorkoutService {

    private final UserRepository userRepository;
    private final WorkoutRepository workoutRepository;
    private final WorkoutMapper workoutMapper;

    public WorkoutServiceImpl(UserRepository userRepository, WorkoutRepository workoutRepository, WorkoutMapper workoutMapper) {
        this.userRepository = userRepository;
        this.workoutRepository = workoutRepository;
        this.workoutMapper = workoutMapper;
    }

    @Transactional
    @Override
    public WorkoutDTO createWorkout(WorkoutCreateDTO workoutCreateDTO) {
        log.debug("Attempting to create workout {}", workoutCreateDTO.getName());
        User user = userRepository.findById(workoutCreateDTO.getUserId()).orElseThrow(
                () -> new UserNotFound("User not found with id " + workoutCreateDTO.getUserId())
        );
        Workout workout = workoutMapper.toWorkout(workoutCreateDTO, user);
        workout = workoutRepository.save(workout);
        log.info("Created workout {} with id {}", workoutCreateDTO.getName(), workout.getId());
        return workoutMapper.toDTO(workout);
    }

    @Override
    public WorkoutDTO getWorkoutByWorkoutId(long id) {
        log.debug("Fetching workout with id: {}", id);
        Workout workout = workoutRepository.findById(id).orElseThrow(
                () -> new WorkoutNotFound("Workout not found with id " + id)
        );
        log.debug("Fetched workout with id: {}", workout.getId());
        return workoutMapper.toDTO(workout);
    }

    @Override
    public List<WorkoutDTO> getWorkouts() {
        return List.of();
    }

    @Override
    public List<WorkoutDTO> getWorkoutByUserId(long id) {
        return List.of();
    }

    @Override
    public WorkoutDTO updateWorkout(WorkoutDTO workoutDTO) {
        return null;
    }

    @Override
    public void deleteWorkoutById(long id) {

    }
}

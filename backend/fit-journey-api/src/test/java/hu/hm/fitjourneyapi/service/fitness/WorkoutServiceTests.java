package hu.hm.fitjourneyapi.service.fitness;

import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutCreateDTO;
import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutDTO;
import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutUpdateDTO;
import hu.hm.fitjourneyapi.exception.fitness.WorkoutNotFound;
import hu.hm.fitjourneyapi.exception.userExceptions.UserNotFound;
import hu.hm.fitjourneyapi.mapper.fitness.WorkoutMapper;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.fitness.Workout;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.fitness.DefaultExercisesRepository;
import hu.hm.fitjourneyapi.repository.fitness.ExerciseRepository;
import hu.hm.fitjourneyapi.repository.fitness.UserMadeTemplateRepository;
import hu.hm.fitjourneyapi.repository.fitness.WorkoutRepository;
import hu.hm.fitjourneyapi.services.implementation.fitness.WorkoutServiceImpl;
import hu.hm.fitjourneyapi.utils.UserTestFactory;
import hu.hm.fitjourneyapi.utils.WorkoutTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class WorkoutServiceTests {
    @Mock
    private WorkoutRepository workoutRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ExerciseRepository exerciseRepository;
    @Mock
    private DefaultExercisesRepository defaultExerciseRepository;
    @Mock
    private UserMadeTemplateRepository userTemplateRepository;
    @Mock
    private WorkoutMapper workoutMapper;

    @InjectMocks
    private WorkoutServiceImpl workoutService;

    private User user;
    private Workout workout;
    private WorkoutDTO workoutDTO;
    private WorkoutCreateDTO workoutCreateDTO;

    @BeforeEach
    void setup() {
        user = UserTestFactory.getUser();
        workout = WorkoutTestFactory.getWorkout(user);
        workout.setId(UUID.randomUUID());
        workoutDTO = WorkoutTestFactory.getWorkoutDTO(user.getId());
        workoutCreateDTO = WorkoutTestFactory.getWorkoutCreateDTO(user.getId());
    }

    @Test
    void createWorkout_Success() {
        when(userRepository.findById(workoutCreateDTO.getUserId())).thenReturn(Optional.of(user));
        when(workoutMapper.toWorkout(workoutCreateDTO, user)).thenReturn(workout);
        when(workoutRepository.save(any(Workout.class))).thenReturn(workout);

        UUID result = workoutService.createWorkout(workoutCreateDTO);

        assertNotNull(result);
        verify(workoutRepository).save(any(Workout.class));
    }

    @Test
    void createWorkout_UserNotFound_ThrowsException() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(UserNotFound.class, () -> workoutService.createWorkout(workoutCreateDTO));
    }

    @Test
    void updateWorkout_Success() {
        UUID workoutId = workout.getId();
        WorkoutUpdateDTO updateDTO = WorkoutUpdateDTO.builder().name("New Name").description("New Description").userId(user.getId()).build();

        when(workoutRepository.findById(workoutId)).thenReturn(Optional.of(workout));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(workoutRepository.save(any(Workout.class))).thenReturn(workout);

        when(workoutMapper.toDTO(any(Workout.class))).thenReturn(
                WorkoutDTO.builder()
                        .id(workoutId)
                        .name(updateDTO.getName())
                        .description(updateDTO.getDescription())
                        .build()
        );

        WorkoutDTO result = workoutService.updateWorkout(workoutId, updateDTO);

        assertEquals("New Name", result.getName());
        verify(workoutRepository).save(workout);
    }

    @Test
    void deleteWorkout_Success() {
        when(workoutRepository.findById(workout.getId())).thenReturn(Optional.of(workout));

        workoutService.deleteWorkoutById(workout.getId());

        verify(workoutRepository, times(1)).delete(workout);
    }

    @Test
    void deleteWorkout_NotFound_ThrowsException() {
        when(workoutRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(WorkoutNotFound.class, () -> workoutService.deleteWorkoutById(UUID.randomUUID()));
    }

    @Test
    void getWorkouts_ReturnsList() {
        when(workoutRepository.findAll()).thenReturn(List.of(workout));
        when(workoutMapper.toDTOList(anyList())).thenReturn(List.of(workoutDTO));

        List<WorkoutDTO> result = workoutService.getWorkouts();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void getWorkoutById_NotFound_ThrowsException() {
        when(workoutRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(WorkoutNotFound.class, () -> workoutService.getWorkoutByWorkoutId(UUID.randomUUID()));
    }
}
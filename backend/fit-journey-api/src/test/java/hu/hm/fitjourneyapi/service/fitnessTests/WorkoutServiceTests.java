package hu.hm.fitjourneyapi.service.fitnessTests;

import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutCreateDTO;
import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutDTO;
import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutUpdateDTO;
import hu.hm.fitjourneyapi.dto.user.UserDTO;
import hu.hm.fitjourneyapi.exception.userExceptions.UserNotFound;
import hu.hm.fitjourneyapi.mapper.fitness.WorkoutMapper;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.fitness.Workout;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.fitness.WorkoutRepository;
import hu.hm.fitjourneyapi.services.interfaces.fitness.WorkoutService;
import hu.hm.fitjourneyapi.utils.UserTestFactory;
import hu.hm.fitjourneyapi.utils.WorkoutTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoBeans;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class WorkoutServiceTests {

    @Autowired
    private WorkoutService workoutService;
    @MockitoBean
    private WorkoutRepository repository;
    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private WorkoutMapper workoutMapper;

    private User user;
    private UserDTO userDTO;
    private Workout workout;
    private WorkoutDTO workoutDTO;
    private WorkoutCreateDTO workoutCreateDTO;

    @BeforeEach
    void setup() {
        user = UserTestFactory.getUser();
        userDTO = UserTestFactory.getUserDTO();
        workout = WorkoutTestFactory.getWorkout(user);
        workoutDTO = WorkoutTestFactory.getWorkoutDTO(1L);
        workoutCreateDTO = WorkoutTestFactory.getWorkoutCreateDTO(1L);
        when(workoutMapper.toWorkout(workoutCreateDTO, user)).thenReturn(workout);
        when(workoutMapper.toDTO(workout)).thenReturn(workoutDTO);
        when(repository.save(workout)).thenReturn(workout);
        when(repository.findWorkoutsByUser_Id(user.getId())).thenReturn(List.of(workout));
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(workout));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

    }

    @Test
    public void WorkoutCreateTest_WorkoutCreated_success() {
        WorkoutDTO result = workoutService.createWorkout(workoutCreateDTO);
        assertNotNull(result);
        assertEquals(workoutDTO.getName(), result.getName());
        assertEquals(workoutDTO.getDescription(), result.getDescription());
        assertEquals(workoutDTO.getUserId(), result.getUserId());
        assertEquals(workoutDTO.getExercises(), result.getExercises());
    }

    @Test
    public void WorkoutCreateTest_WorkoutCreated_fail() {
        when(userRepository.findById(workoutCreateDTO.getUserId())).thenThrow(UserNotFound.class);
        assertThrows(UserNotFound.class, () -> workoutService.createWorkout(workoutCreateDTO));
    }

    @Test
    public void WorkoutUpdateTest_WorkoutUpdated_success() {
        long idToUpdate = workoutDTO.getId();
        WorkoutUpdateDTO update = WorkoutUpdateDTO.builder()
                .name("Updated name")
                .description("Updated desc")
                .lengthInMins(10)
                .build();

        WorkoutDTO result = workoutService.updateWorkout(idToUpdate,workoutDTO);
        assertNotNull(result);
        assertEquals(workoutDTO.getName(), result.getName());
        assertEquals(workoutDTO.getDescription(), result.getDescription());
        assertEquals(workoutDTO.getUserId(), result.getUserId());
        assertEquals(workoutDTO.getExercises(), result.getExercises());
    }
}

package hu.hm.fitjourneyapi.service.fitnessTests;

import hu.hm.fitjourneyapi.dto.*;
import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutCreateDTO;
import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutDTO;
import hu.hm.fitjourneyapi.dto.user.UserCreateDTO;
import hu.hm.fitjourneyapi.dto.user.UserDTO;
import hu.hm.fitjourneyapi.mapper.fitness.WorkoutMapper;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.fitness.Workout;
import hu.hm.fitjourneyapi.repository.fitness.WorkoutRepository;
import hu.hm.fitjourneyapi.services.interfaces.fitness.WorkoutService;
import hu.hm.fitjourneyapi.utils.UserTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class WorkoutServiceTests {

    private final WorkoutService workoutService;
    @MockitoBean
    private WorkoutRepository repository;
    @MockitoBean
    private WorkoutMapper workoutMapper;

    private User user;
    private UserDTO userDTO;
    private Workout workout;
    private WorkoutDTO workoutDTO;
    private WorkoutCreateDTO workoutCreateDTO;


    public WorkoutServiceTests(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @BeforeEach
    void setup() {
        user = UserTestFactory.getUser();
        userDTO = UserTestFactory.getUserDTO();
        when(workoutMapper.toWorkout(workoutCreateDTO, user)).thenReturn(workout);
        when(workoutMapper.toDTO(workout)).thenReturn(workoutDTO);
        when(repository.save(workout)).thenReturn(workout);
        when(repository.findWorkoutsByUser_Id(user.getId())).thenReturn(List.of(workout));
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(workout));
    }

}

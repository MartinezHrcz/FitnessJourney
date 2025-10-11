package hu.hm.fitjourneyapi.service.fitnessTests;

import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutCreateDTO;
import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutDTO;
import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutUpdateDTO;
import hu.hm.fitjourneyapi.dto.user.UserDTO;
import hu.hm.fitjourneyapi.exception.fitness.WorkoutNotFound;
import hu.hm.fitjourneyapi.exception.userExceptions.UserNotFound;
import hu.hm.fitjourneyapi.mapper.fitness.WorkoutMapper;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.fitness.Workout;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.fitness.WorkoutRepository;
import hu.hm.fitjourneyapi.services.interfaces.fitness.WorkoutService;
import hu.hm.fitjourneyapi.utils.UserTestFactory;
import hu.hm.fitjourneyapi.utils.WorkoutTestFactory;
import org.hibernate.jdbc.Work;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

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
    @Autowired
    private WorkoutRepository workoutRepository;

    @BeforeEach
    void setup() {
        user = UserTestFactory.getUser();
        userDTO = UserTestFactory.getUserDTO();
        workout = WorkoutTestFactory.getWorkout(user);
        workoutDTO = WorkoutTestFactory.getWorkoutDTO(1L);
        workoutCreateDTO = WorkoutTestFactory.getWorkoutCreateDTO(1L);
        when(workoutMapper.toWorkout(workoutCreateDTO, user)).thenReturn(workout);
        when(repository.save(workout)).thenReturn(workout);
        when(repository.findWorkoutsByUser_Id(user.getId())).thenReturn(List.of(workout));
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(workout));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(workoutMapper.toDTO(workout))
                .thenAnswer(invocation ->
                        {
                            Workout w = invocation.getArgument(0);
                            return WorkoutDTO.builder()
                                    .id(w.getId())
                                    .name(w.getName())
                                    .description(w.getDescription())
                                    .userId(user.getId())
                                    .lengthInMins(w.getLengthInMins())
                                    .exercises(w.getExercises())
                                    .build();
                        }
                        );
    }

    @Test
    public void WorkoutCreateTest_WorkoutCreated_success() {
        WorkoutDTO result = workoutService.createWorkout(workoutCreateDTO);
        assertNotNull(result);
        assertEquals(workoutDTO.getName(), result.getName());
        assertEquals(workoutDTO.getDescription(), result.getDescription());
        assertEquals(workoutDTO.getUserId(), result.getUserId());
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
                .userId(userDTO.getId())
                .build();

        WorkoutDTO result = workoutService.updateWorkout(idToUpdate, update);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(update.getName(), result.getName());
        assertEquals(update.getDescription(), result.getDescription());
        assertEquals(update.getUserId(), result.getUserId());
        assertEquals(update.getExercises(), result.getExercises());
    }

    @Test
    public void WorkoutUpdateTest_WorkoutIdNotFound_fail() {
        long idToUpdate = workoutDTO.getId();
        WorkoutUpdateDTO update = WorkoutUpdateDTO.builder()
                .name("Updated name")
                .description("Updated desc")
                .userId(userDTO.getId())
                .lengthInMins(10)
                .build();
        when(workoutRepository.findById(idToUpdate)).thenThrow(WorkoutNotFound.class);
        assertThrows(WorkoutNotFound.class, () -> workoutService.updateWorkout(idToUpdate, update));
    }

    @Test
    public void WorkoutUpdateTest_UserIdNotFound_fail() {
        long idToUpdate = workoutDTO.getId();
        WorkoutUpdateDTO update = WorkoutUpdateDTO.builder()
                .name("Updated name")
                .description("Updated desc")
                .lengthInMins(10)
                .userId(userDTO.getId())
                .build();
        when(workoutRepository.findById(idToUpdate)).thenThrow(WorkoutNotFound.class);
        assertThrows(WorkoutNotFound.class, () -> workoutService.updateWorkout(idToUpdate, update));
    }

    @Test
    public void WorkoutDeleteTest_WorkoutDeleted_success() {
        long idToDelete = workoutDTO.getId();
        when(workoutRepository.findById(idToDelete)).thenReturn(Optional.ofNullable(workout));
        workoutRepository.deleteById(idToDelete);
        verify(workoutRepository, times(1)).deleteById(idToDelete);
    }

    @Test
    public void WorkoutDeleteTest_WorkoutIdNotFound_fail() {
        long idToDelete = workoutDTO.getId();
        when(workoutRepository.findById(idToDelete)).thenThrow(WorkoutNotFound.class);
        assertThrows(WorkoutNotFound.class, () -> workoutRepository.deleteById(idToDelete));
    }

    @Test
    public void WorkoutGetAllTest_WorkoutsGet_success(){
        List<WorkoutDTO> result = workoutService.getWorkouts();
        assertNotNull(result);
        assertEquals(workoutDTO.getName(), result.getFirst().getName());
        assertEquals(workoutDTO.getDescription(), result.getFirst().getDescription());
        assertEquals(workoutDTO.getUserId(), result.getFirst().getUserId());
        assertEquals(workoutDTO.getExercises(), result.getFirst().getExercises());
    }

    @Test
    public void WorkoutGetByIdTest_WorkoutGet_success(){
        WorkoutDTO result = workoutService.getWorkoutByWorkoutId(1L);
        assertNotNull(result);
        assertEquals(workoutDTO.getName(), result.getName());
        assertEquals(workoutDTO.getDescription(), result.getDescription());
        assertEquals(workoutDTO.getUserId(), result.getUserId());
        assertEquals(workoutDTO.getExercises(), result.getExercises());
    }

    @Test
    public void WorkoutGetByIdTest_WorkoutIdNotFound_fail(){
        when(workoutRepository.findById(1L)).thenThrow(WorkoutNotFound.class);
        assertThrows(WorkoutNotFound.class, () -> workoutService.getWorkoutByWorkoutId(1L));
    }

    @Test
    public void WorkoutGetByUserIdTest_WorkoutsGet_success(){
        when(workoutRepository.findWorkoutsByUser_Id(1L)).thenReturn(List.of(workout));
        List<WorkoutDTO> result = workoutService.getWorkoutByUserId(1L);
        assertNotNull(result);
        assertEquals(workoutDTO.getName(), result.getFirst().getName());
        assertEquals(workoutDTO.getDescription(), result.getFirst().getDescription());
        assertEquals(workoutDTO.getUserId(), result.getFirst().getUserId());
        assertEquals(workoutDTO.getExercises(), result.getFirst().getExercises());
    }
}

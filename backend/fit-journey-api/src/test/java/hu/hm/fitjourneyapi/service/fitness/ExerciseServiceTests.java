package hu.hm.fitjourneyapi.service.fitness;

import hu.hm.fitjourneyapi.dto.fitness.excercise.AbstractExerciseDTO;
import hu.hm.fitjourneyapi.dto.fitness.excercise.ExerciseUpdateDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.StrengthSetDTO;
import hu.hm.fitjourneyapi.exception.fitness.ExerciseNotFound;
import hu.hm.fitjourneyapi.mapper.fitness.ExerciseMapper;
import hu.hm.fitjourneyapi.mapper.fitness.SetMapper;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.fitness.Exercise;
import hu.hm.fitjourneyapi.model.fitness.StrengthSet;
import hu.hm.fitjourneyapi.model.fitness.Workout;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.fitness.ExerciseRepository;
import hu.hm.fitjourneyapi.repository.fitness.SetRepository;
import hu.hm.fitjourneyapi.repository.fitness.WorkoutRepository;
import hu.hm.fitjourneyapi.services.implementation.fitness.ExerciseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ExerciseServiceTests {

    @Mock
    private ExerciseRepository exerciseRepository;
    @Mock
    private WorkoutRepository workoutRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private SetRepository setRepository;
    @Mock
    private ExerciseMapper exerciseMapper;
    @Mock
    private SetMapper setMapper;

    @InjectMocks
    private ExerciseServiceImpl exerciseService;

    private Exercise exercise;
    private AbstractExerciseDTO exerciseDTO;
    private UUID exerciseId;

    @BeforeEach
    void setUp() {
        exerciseId = UUID.randomUUID();
        exercise = new Exercise();
        exercise.setId(exerciseId);
        exercise.setName("Bench Press");
        exercise.setSets(new ArrayList<>());

        exerciseDTO = mock(AbstractExerciseDTO.class);
    }

    @Test
    void getById_Success() {
        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.of(exercise));
        when(exerciseMapper.toExerciseDTO(exercise)).thenReturn(exerciseDTO);

        AbstractExerciseDTO result = exerciseService.getById(exerciseId);

        assertNotNull(result);
        verify(exerciseRepository).findById(exerciseId);
    }

    @Test
    void getById_NotFound_ThrowsException() {
        when(exerciseRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ExerciseNotFound.class, () -> exerciseService.getById(UUID.randomUUID()));
    }

    @Test
    void getByWorkoutId_Success() {
        UUID workoutId = UUID.randomUUID();
        Workout workout = new Workout();
        workout.setExercises(List.of(exercise));

        when(workoutRepository.getReferenceById(workoutId)).thenReturn(workout);
        when(exerciseMapper.toExerciseDTOs(anyList())).thenReturn(List.of(exerciseDTO));

        List<AbstractExerciseDTO> result = exerciseService.getByWorkoutId(workoutId);

        assertEquals(1, result.size());
        verify(workoutRepository).getReferenceById(workoutId);
    }

    @Test
    void getByUserId_Success() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        Workout workout = new Workout();
        workout.setExercises(List.of(exercise));
        user.setWorkouts(List.of(workout));

        when(userRepository.getReferenceById(userId)).thenReturn(user);
        when(exerciseMapper.toExerciseDTOs(anyList())).thenReturn(List.of(exerciseDTO));

        List<AbstractExerciseDTO> result = exerciseService.getByUserId(userId);

        assertFalse(result.isEmpty());
        verify(userRepository).getReferenceById(userId);
    }

    @Test
    void updateExercise_Success() {
        ExerciseUpdateDTO updateDTO = ExerciseUpdateDTO.builder().name("New set").build();

        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.of(exercise));
        when(exerciseRepository.save(exercise)).thenReturn(exercise);
        when(exerciseMapper.toExerciseDTO(exercise)).thenReturn(exerciseDTO);

        AbstractExerciseDTO result = exerciseService.updateExercise(exerciseId, updateDTO);

        assertNotNull(result);
        verify(exerciseMapper).updateExerciseFields(exercise, updateDTO);
        verify(exerciseRepository).save(exercise);
    }

    @Test
    void addSetById_Success() {
        StrengthSetDTO setDTO = new StrengthSetDTO();
        StrengthSet setEntity = new StrengthSet();

        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.of(exercise));
        when(setMapper.toEntity(setDTO, exercise)).thenReturn(setEntity);
        when(exerciseMapper.toExerciseDTO(exercise)).thenReturn(exerciseDTO);

        AbstractExerciseDTO result = exerciseService.addSetById(exerciseId, setDTO);

        assertNotNull(result);
        verify(exerciseRepository).save(exercise);
        assertTrue(exercise.getSets().contains(setEntity));
    }

    @Test
    void updateSetById_StrengthSet_Success() {
        long setId = 100L;
        StrengthSet existingSet = new StrengthSet();
        StrengthSetDTO updateSetDTO = new StrengthSetDTO();
        updateSetDTO.setReps(10);
        updateSetDTO.setWeight(100);

        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.of(exercise));
        when(setRepository.findById(setId)).thenReturn(Optional.of(existingSet));
        when(exerciseRepository.save(exercise)).thenReturn(exercise);
        when(exerciseMapper.toExerciseDTO(exercise)).thenReturn(exerciseDTO);

        exerciseService.updateSetById(exerciseId, setId, updateSetDTO);

        assertEquals(10, existingSet.getReps());
        assertEquals(100, existingSet.getWeight());
        verify(setRepository).save(existingSet);
    }

    @Test
    void removeSetById_Success() {
        long setId = 100L;
        StrengthSet setToRemove = new StrengthSet();
        setToRemove.setId(setId);
        exercise.getSets().add(setToRemove);

        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.of(exercise));
        when(setRepository.findById(setId)).thenReturn(Optional.of(setToRemove));
        when(exerciseRepository.save(exercise)).thenReturn(exercise);
        when(exerciseMapper.toExerciseDTO(exercise)).thenReturn(exerciseDTO);

        AbstractExerciseDTO result = exerciseService.removeSetById(exerciseId, setId);

        assertFalse(exercise.getSets().contains(setToRemove));
        verify(setRepository).delete(setToRemove);
    }

    @Test
    void deleteExercise_Success() {
        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.of(exercise));

        exerciseService.deleteExerciseById(exerciseId);

        verify(exerciseRepository).delete(exercise);
    }
}
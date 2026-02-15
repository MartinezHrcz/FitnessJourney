package hu.hm.fitjourneyapi.service.fitness;

import hu.hm.fitjourneyapi.dto.fitness.set.AbstractSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.StrengthSetDTO;
import hu.hm.fitjourneyapi.exception.fitness.SetNotFound;
import hu.hm.fitjourneyapi.exception.fitness.setExceptions.InvalidSetType;
import hu.hm.fitjourneyapi.mapper.fitness.SetMapper;
import hu.hm.fitjourneyapi.model.fitness.Exercise;
import hu.hm.fitjourneyapi.model.fitness.Set;
import hu.hm.fitjourneyapi.model.fitness.StrengthSet;
import hu.hm.fitjourneyapi.model.fitness.CardioSet;
import hu.hm.fitjourneyapi.repository.fitness.ExerciseRepository;
import hu.hm.fitjourneyapi.repository.fitness.SetRepository;
import hu.hm.fitjourneyapi.services.implementation.fitness.SetServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SetServiceTests {

    @Mock
    private SetRepository setRepository;
    @Mock
    private ExerciseRepository exerciseRepository;
    @Mock
    private SetMapper setMapper;

    @InjectMocks
    private SetServiceImpl setService;

    private Exercise mockExercise;
    private Set strengthSet;
    private AbstractSetDTO strengthSetDTO;
    private final long setId = 1L;

    @BeforeEach
    void setUp() {
        mockExercise = new Exercise();
        mockExercise.setId(UUID.randomUUID());

        strengthSet = new StrengthSet();
        strengthSet.setId(setId);
        strengthSet.setExercise(mockExercise);

        strengthSetDTO = mock(StrengthSetDTO.class);
        when(strengthSetDTO.getExerciseId()).thenReturn(mockExercise.getId());
    }

    @Test
    void getSetBySetId_Success() {
        when(setRepository.findById(setId)).thenReturn(Optional.of(strengthSet));
        when(setMapper.toDto(strengthSet, mockExercise)).thenReturn(strengthSetDTO);

        AbstractSetDTO result = setService.getSetBySetId(setId);

        assertNotNull(result);
        verify(setRepository).findById(setId);
    }

    @Test
    void getSetBySetId_NotFound_ThrowsException() {
        when(setRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(SetNotFound.class, () -> setService.getSetBySetId(setId));
    }

    @Test
    void getSets_Success() {
        when(setRepository.findAll()).thenReturn(List.of(strengthSet));
        when(setMapper.toDto(any(), any())).thenReturn(strengthSetDTO);

        List<AbstractSetDTO> result = setService.getSets();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void createSet_Success() {
        when(exerciseRepository.findById(mockExercise.getId())).thenReturn(Optional.of(mockExercise));
        when(setMapper.toEntity(strengthSetDTO, mockExercise)).thenReturn(strengthSet);
        when(setRepository.save(strengthSet)).thenReturn(strengthSet);
        when(setMapper.toDto(strengthSet, mockExercise)).thenReturn(strengthSetDTO);

        AbstractSetDTO result = setService.createSet(strengthSetDTO);

        assertNotNull(result);
        verify(setRepository).save(strengthSet);
    }

    @Test
    void updateSet_Success() {
        StrengthSet existingSet = new StrengthSet();
        existingSet.setId(setId);
        existingSet.setExercise(mockExercise);

        StrengthSet updateEntity = new StrengthSet();
        updateEntity.setExercise(mockExercise);

        when(setRepository.findById(setId)).thenReturn(Optional.of(existingSet));
        when(setMapper.toEntity(strengthSetDTO, mockExercise)).thenReturn(updateEntity);
        when(setRepository.save(existingSet)).thenReturn(existingSet);
        when(setMapper.toDto(existingSet, mockExercise)).thenReturn(strengthSetDTO);

        AbstractSetDTO result = setService.updateSet(setId, strengthSetDTO);

        assertNotNull(result);
        verify(setRepository).save(existingSet);
    }

    @Test
    void updateSet_InvalidType_ThrowsException() {
        StrengthSet existingStrengthSet = new StrengthSet();
        existingStrengthSet.setId(setId);
        existingStrengthSet.setExercise(mockExercise);

        CardioSet mappedCardioSet = new CardioSet();

        when(setRepository.findById(setId)).thenReturn(Optional.of(existingStrengthSet));
        when(setMapper.toEntity(any(), any())).thenReturn(mappedCardioSet);

        assertThrows(InvalidSetType.class, () -> setService.updateSet(setId, strengthSetDTO));
    }

    @Test
    void deleteSetById_Success() {
        when(setRepository.findById(setId)).thenReturn(Optional.of(strengthSet));

        setService.deleteSetById(setId);

        verify(setRepository).delete(strengthSet);
    }

    @Test
    void deleteSetById_NotFound_ThrowsException() {
        when(setRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(SetNotFound.class, () -> setService.deleteSetById(setId));
    }
}
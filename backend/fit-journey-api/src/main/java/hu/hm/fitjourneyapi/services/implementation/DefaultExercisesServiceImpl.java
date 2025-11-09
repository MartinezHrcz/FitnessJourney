package hu.hm.fitjourneyapi.services.implementation;

import hu.hm.fitjourneyapi.dto.fitness.exerciseTemplates.DefaultExerciseDTO;
import hu.hm.fitjourneyapi.mapper.fitness.DefaultExerciseMapper;
import hu.hm.fitjourneyapi.model.fitness.DefaultExercise;
import hu.hm.fitjourneyapi.repository.fitness.DefaultExercisesRepository;
import hu.hm.fitjourneyapi.services.interfaces.fitness.DefaultExerciseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class DefaultExercisesServiceImpl implements DefaultExerciseService {

    private final DefaultExercisesRepository defaultExcRepo;
    private final DefaultExerciseMapper  defaultExerciseMapper;

    public DefaultExercisesServiceImpl(DefaultExercisesRepository defaultExcRepo, DefaultExerciseMapper defaultExerciseMapper) {
        this.defaultExcRepo = defaultExcRepo;
        this.defaultExerciseMapper = defaultExerciseMapper;
    }


    @Override
    public List<DefaultExerciseDTO> getDefaultExercises() {
        return defaultExerciseMapper.toDto(defaultExcRepo.findAll());
    }

    @Override
    public DefaultExerciseDTO getDefaultExercise(long id) {
        DefaultExercise exercise = defaultExcRepo.findById(id).orElseThrow(
                ()-> new NoSuchElementException("Exercise with id " + id + " not found")
        );
        return defaultExerciseMapper.toDto(exercise);
    }

    @Override
    public List<DefaultExerciseDTO> getDefaultExercisesByName(String name) {
        return defaultExerciseMapper.toDto(defaultExcRepo.findAllByNameContainingIgnoreCase(name));
    }
}

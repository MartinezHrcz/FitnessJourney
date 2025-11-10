package hu.hm.fitjourneyapi.services.implementation.fitness.exerciseTemplates;

import hu.hm.fitjourneyapi.dto.fitness.exerciseTemplates.UserExerciseUpdateDto;
import hu.hm.fitjourneyapi.dto.fitness.exerciseTemplates.UserMadeExercisesDTO;
import hu.hm.fitjourneyapi.exception.fitness.ExerciseNotFound;
import hu.hm.fitjourneyapi.mapper.fitness.UserMadeExercisesMapper;
import hu.hm.fitjourneyapi.model.fitness.UserMadeTemplates;
import hu.hm.fitjourneyapi.repository.fitness.UserMadeTemplateRepository;
import hu.hm.fitjourneyapi.services.interfaces.fitness.UserExerciseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserExerciseServiceImpl implements UserExerciseService {

    private final UserMadeTemplateRepository repository;
    private final UserMadeExercisesMapper mapper;

    public UserExerciseServiceImpl(UserMadeTemplateRepository repository, UserMadeExercisesMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    @Override
    public List<UserMadeExercisesDTO> getUserMadeExercises(long id) {
        log.debug("Getting all user made templates");
        return mapper.toDto(repository.findAll());
    }

    @Override
    public List<UserMadeExercisesDTO> getUserMadeExercisesByName(String name) {
        log.debug("Getting all user made templates by name");
        return mapper.toDto(repository.findAllByNameContainingIgnoreCase(name));
    }

    @Override
    public UserMadeExercisesDTO getUserMadeExercise(long id) {
        log.debug("Getting user made exercise by id");
        UserMadeTemplates template = repository.findById(id).orElseThrow(
                () -> new ExerciseNotFound("Exercise not found by id: " + id)
        );

        log.info("Got user made template");
        return mapper.toDto(template);
    }

    @Override
    public UserMadeExercisesDTO createUserMadeExercise(UserExerciseUpdateDto dto) {
        log.debug("Attempting to create user template");
        UserMadeTemplates template = mapper.toEntity(dto);
        template = repository.save(template);
        log.info("Created user made exercise by id: " + template.getId());
        return  mapper.toDto(template);
    }

    @Override
    public UserMadeExercisesDTO updateUserMadeExercise(long id, UserExerciseUpdateDto dto) {
        return null;
    }

    @Override
    public void deleteUserMadeExercise(long id) {

    }
}

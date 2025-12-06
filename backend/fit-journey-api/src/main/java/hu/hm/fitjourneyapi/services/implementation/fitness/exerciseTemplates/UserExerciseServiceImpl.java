package hu.hm.fitjourneyapi.services.implementation.fitness.exerciseTemplates;

import hu.hm.fitjourneyapi.dto.fitness.exerciseTemplates.UserExerciseUpdateDto;
import hu.hm.fitjourneyapi.dto.fitness.exerciseTemplates.UserMadeExercisesDTO;
import hu.hm.fitjourneyapi.exception.fitness.ExerciseNotFound;
import hu.hm.fitjourneyapi.exception.userExceptions.UserNotFound;
import hu.hm.fitjourneyapi.mapper.fitness.UserMadeExercisesMapper;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.fitness.UserMadeTemplates;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.fitness.UserMadeTemplateRepository;
import hu.hm.fitjourneyapi.services.interfaces.fitness.UserExerciseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserExerciseServiceImpl implements UserExerciseService {

    private final UserMadeTemplateRepository repository;
    private final UserRepository userRepository;
    private final UserMadeExercisesMapper mapper;

    public UserExerciseServiceImpl(UserMadeTemplateRepository repository, UserRepository userRepository, UserMadeExercisesMapper mapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }


    @Override
    public List<UserMadeExercisesDTO> getUserMadeExercises() {
        log.debug("Getting all user made templates");
        return mapper.toDto(repository.findAll());
    }

    @Override
    public List<UserMadeExercisesDTO> getUserMadeExercisesByName(String name) {
        log.debug("Getting all user made templates by name");
        return mapper.toDto(repository.findAllByNameContainingIgnoreCase(name));
    }

    @Override
    public UserMadeExercisesDTO getUserMadeExercise(UUID id) {
        log.debug("Getting user made exercise by id");
        UserMadeTemplates template = repository.findById(id).orElseThrow(
                () -> new ExerciseNotFound("Exercise not found by id: " + id)
        );

        log.info("Got user made template");
        return mapper.toDto(template);
    }

    @Override
    public List<UserMadeExercisesDTO> getUserMadeExercisesByUser(UUID userId) {
        List<UserMadeTemplates> templates = repository.findAllByUserId(userId);
        return mapper.toDto(templates);
    }

    @Override
    public UserMadeExercisesDTO createUserMadeExercise(UUID userId,UserExerciseUpdateDto dto) {
        log.debug("Attempting to create user template");
        UserMadeTemplates template = mapper.toEntity(dto);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFound("User not found with id: " + userId)
        );
        template.setUser(user);
        template = repository.save(template);
        log.info("Created user made exercise by id: " + template.getId());
        return  mapper.toDto(template);
    }

    @Override
    public UserMadeExercisesDTO updateUserMadeExercise(UUID id, UserExerciseUpdateDto dto) {
        log.debug("Attempting to update user template");
        UserMadeTemplates template =  repository.findById(id).orElseThrow(
                () -> new ExerciseNotFound("Exercise not found by id: " + id)
        );
        template.setDescription(dto.getDescription());
        template.setName(dto.getName());
        template.setType(dto.getType());
        template.setWeightType(dto.getWeightType());

        template =  repository.save(template);
        log.info("Updated user made exercise by id: " + template.getId());

        return mapper.toDto(template);
    }

    @Override
    public void deleteUserMadeExercise(UUID id) {
        log.debug("Attempting to delete user template");
        UserMadeTemplates template = repository.findById(id).orElseThrow(
                () -> new ExerciseNotFound("Exercise not found by id: " + id));
        repository.delete(template);
    }
}

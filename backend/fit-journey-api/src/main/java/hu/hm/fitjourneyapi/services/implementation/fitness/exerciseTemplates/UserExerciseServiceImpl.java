package hu.hm.fitjourneyapi.services.implementation.fitness.exerciseTemplates;

import hu.hm.fitjourneyapi.dto.fitness.exerciseTemplates.UserExerciseUpdateDto;
import hu.hm.fitjourneyapi.dto.fitness.exerciseTemplates.UserMadeExercisesDTO;
import hu.hm.fitjourneyapi.services.interfaces.fitness.UserExerciseService;

import java.util.List;

public class UserExerciseServiceImpl implements UserExerciseService {

    private final UserMadeTemplatesRepository


    @Override
    public List<UserMadeExercisesDTO> getUserMadeExercises(long id) {
        return List.of();
    }

    @Override
    public List<UserMadeExercisesDTO> getUserMadeExercisesByName(String name) {
        return List.of();
    }

    @Override
    public UserMadeExercisesDTO getUserMadeExercise(long id) {
        return null;
    }

    @Override
    public UserMadeExercisesDTO createUserMadeExercise(UserMadeExercisesDTO dto) {
        return null;
    }

    @Override
    public UserMadeExercisesDTO updateUserMadeExercise(long id, UserExerciseUpdateDto dto) {
        return null;
    }

    @Override
    public void deleteUserMadeExercise(long id) {

    }
}

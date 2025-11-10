package hu.hm.fitjourneyapi.services.interfaces.fitness;

import hu.hm.fitjourneyapi.dto.fitness.exerciseTemplates.UserExerciseUpdateDto;
import hu.hm.fitjourneyapi.dto.fitness.exerciseTemplates.UserMadeExercisesDTO;

import java.util.List;
import java.util.UUID;

public interface UserExerciseService {
    List<UserMadeExercisesDTO>  getUserMadeExercises(long id);
    List<UserMadeExercisesDTO> getUserMadeExercisesByName(String name);
    UserMadeExercisesDTO getUserMadeExercise(long id);
    List<UserMadeExercisesDTO> getUserMadeExercisesByUser(UUID userId);
    UserMadeExercisesDTO createUserMadeExercise(UserExerciseUpdateDto dto);
    UserMadeExercisesDTO updateUserMadeExercise(long id, UserExerciseUpdateDto dto);
    void deleteUserMadeExercise(long id);
}

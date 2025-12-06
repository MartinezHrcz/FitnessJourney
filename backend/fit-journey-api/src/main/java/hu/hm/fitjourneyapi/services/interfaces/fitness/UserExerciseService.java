package hu.hm.fitjourneyapi.services.interfaces.fitness;

import hu.hm.fitjourneyapi.dto.fitness.exerciseTemplates.UserExerciseUpdateDto;
import hu.hm.fitjourneyapi.dto.fitness.exerciseTemplates.UserMadeExercisesDTO;

import java.util.List;
import java.util.UUID;

public interface UserExerciseService {
    List<UserMadeExercisesDTO>  getUserMadeExercises();
    List<UserMadeExercisesDTO> getUserMadeExercisesByName(String name);
    UserMadeExercisesDTO getUserMadeExercise(UUID id);
    List<UserMadeExercisesDTO> getUserMadeExercisesByUser(UUID userId);
    UserMadeExercisesDTO createUserMadeExercise(UUID user,UserExerciseUpdateDto dto);
    UserMadeExercisesDTO updateUserMadeExercise(UUID id, UserExerciseUpdateDto dto);
    void deleteUserMadeExercise(UUID id);
}

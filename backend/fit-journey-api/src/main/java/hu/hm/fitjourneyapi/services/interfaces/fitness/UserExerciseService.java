package hu.hm.fitjourneyapi.services.interfaces.fitness;

import hu.hm.fitjourneyapi.dto.fitness.exerciseTemplates.UserExerciseUpdateDto;
import hu.hm.fitjourneyapi.dto.fitness.exerciseTemplates.UserMadeExercisesDTO;

import java.util.List;

public interface UserExerciseService {
    List<UserMadeExercisesDTO>  getUserMadeExercises(long id);
    List<UserMadeExercisesDTO> getUserMadeExercisesByName(String name);
    UserMadeExercisesDTO getUserMadeExercise(long id);
    UserMadeExercisesDTO createUserMadeExercise(UserExerciseUpdateDto dto);
    UserMadeExercisesDTO updateUserMadeExercise(long id, UserExerciseUpdateDto dto);
    void deleteUserMadeExercise(long id);
}

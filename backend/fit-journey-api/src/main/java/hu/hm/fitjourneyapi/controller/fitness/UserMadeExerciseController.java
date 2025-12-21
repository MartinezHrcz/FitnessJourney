package hu.hm.fitjourneyapi.controller.fitness;

import hu.hm.fitjourneyapi.dto.fitness.exerciseTemplates.UserExerciseUpdateDto;
import hu.hm.fitjourneyapi.dto.fitness.exerciseTemplates.UserMadeExercisesDTO;
import hu.hm.fitjourneyapi.exception.fitness.ExerciseNotFound;
import hu.hm.fitjourneyapi.exception.userExceptions.UserNotFound;
import hu.hm.fitjourneyapi.services.interfaces.fitness.UserExerciseService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/user/templates")
public class UserMadeExerciseController {

    private final UserExerciseService userExerciseService;

    public UserMadeExerciseController(UserExerciseService userExerciseService) {
        this.userExerciseService = userExerciseService;
    }

    @GetMapping
    public ResponseEntity<List<UserMadeExercisesDTO>> getAll() {
        return ResponseEntity.ok(userExerciseService.getUserMadeExercises());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserMadeExercisesDTO> getById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(userExerciseService.getUserMadeExercise(id));
        } catch (ExerciseNotFound ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/byname/{name}")
    public ResponseEntity<List<UserMadeExercisesDTO>> getByName(@PathVariable String name) {
        return ResponseEntity.ok(userExerciseService.getUserMadeExercisesByName(name));
    }

    @GetMapping("/byuser/{id}")
    public ResponseEntity<List<UserMadeExercisesDTO>> getByUser(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(userExerciseService.getUserMadeExercisesByUser(id));
        } catch (ExerciseNotFound ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<UserMadeExercisesDTO> createUserTemplate(@PathVariable UUID userId, @RequestBody UserExerciseUpdateDto dto) {
        try {
            return ResponseEntity.ok(userExerciseService.createUserMadeExercise(userId, dto));
        } catch (UserNotFound ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserMadeExercisesDTO> updateUserTemplate(@PathVariable UUID id, @RequestBody UserExerciseUpdateDto dto) {
        try {
            return ResponseEntity.ok(userExerciseService.updateUserMadeExercise(id, dto));
        } catch (ExerciseNotFound ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserTemplate(@PathVariable UUID id) {
        try {
            userExerciseService.deleteUserMadeExercise(id);
            return ResponseEntity.ok("Exercise template deleted");
        } catch (ExerciseNotFound ex) {
            return ResponseEntity.notFound().build();
        }
    }
}

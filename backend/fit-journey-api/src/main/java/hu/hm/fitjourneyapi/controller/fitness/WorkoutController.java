package hu.hm.fitjourneyapi.controller.fitness;

import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutCreateDTO;
import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutDTO;
import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutUpdateDTO;
import hu.hm.fitjourneyapi.exception.fitness.ExerciseNotFound;
import hu.hm.fitjourneyapi.exception.fitness.WorkoutNotFound;
import hu.hm.fitjourneyapi.exception.userExceptions.UserNotFound;
import hu.hm.fitjourneyapi.services.interfaces.fitness.WorkoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/workout")
public class WorkoutController {
    WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @PostMapping
    public ResponseEntity<UUID> createWorkout(@RequestBody WorkoutCreateDTO workoutCreateDTO) {
        try {
            return ResponseEntity.ok(workoutService.createWorkout(workoutCreateDTO));
        } catch (UserNotFound ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutDTO> getWorkout(@PathVariable UUID id) {
        return ResponseEntity.ok(workoutService.getWorkoutByWorkoutId(id));
    }

    @GetMapping("/byuser/{id}")
    public ResponseEntity<List<WorkoutDTO>> getWorkoutByUser(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(workoutService.getWorkoutByUserId(id));

        } catch (UserNotFound ex) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping
    public ResponseEntity<List<WorkoutDTO>> getAllWorkouts() {
        return ResponseEntity.ok(workoutService.getWorkouts());
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutDTO> updateWorkout(@PathVariable UUID id, @RequestBody WorkoutUpdateDTO workoutUpdateDTO) {
        try {
            return ResponseEntity.ok(workoutService.updateWorkout(id, workoutUpdateDTO));
        } catch (UserNotFound ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/finish")
    public ResponseEntity<WorkoutDTO> finishWorkout(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(workoutService.finishWorkout(id));
        }
        catch (WorkoutNotFound ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/addexc/{id}-{exerciseId}")
    public ResponseEntity<WorkoutDTO> addExerciseToWorkout(@PathVariable UUID id, @PathVariable UUID exerciseId) {
        try {
            return ResponseEntity.ok(workoutService.addExerciseToWorkout(id, exerciseId));
        } catch (ExerciseNotFound | WorkoutNotFound ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("addexc/template/default/{id}-{templateId}")
    public ResponseEntity<WorkoutDTO> addDefaultExerciseToWorkout(@PathVariable UUID id, @PathVariable UUID templateId) {
        try {
            return ResponseEntity.ok(workoutService.addDefaultExerciseToWorkout(id, templateId));
        } catch (ExerciseNotFound | WorkoutNotFound ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("addexc/template/usermade/{id}-{template}")
    public ResponseEntity<WorkoutDTO> addUserExerciseToWorkout(@PathVariable UUID id, @PathVariable UUID template) {
        try {
            return ResponseEntity.ok(workoutService.addUserExerciseToWorkout(id, template));
        } catch (ExerciseNotFound | WorkoutNotFound ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/rmexc/{id}-{exerciseId}")
    public ResponseEntity<WorkoutDTO> removeExerciseFromWorkout(@PathVariable UUID id, @PathVariable UUID exerciseId) {
        try {
            return ResponseEntity.ok(workoutService.removeExerciseFromWorkout(id, exerciseId));
        } catch (ExerciseNotFound | WorkoutNotFound ex) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorkout(UUID id) {
        try {
            workoutService.deleteWorkoutById(id);
            return ResponseEntity.ok("Workout with id " + id + " deleted");
        } catch (WorkoutNotFound ex) {
            return ResponseEntity.notFound().build();
        }
    }

}

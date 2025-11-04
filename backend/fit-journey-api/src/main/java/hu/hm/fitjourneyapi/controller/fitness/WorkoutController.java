package hu.hm.fitjourneyapi.controller.fitness;

import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutCreateDTO;
import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutDTO;
import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutUpdateDTO;
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
    public ResponseEntity<WorkoutDTO> createWorkout(@RequestBody WorkoutCreateDTO  workoutCreateDTO) {
        try {
            return ResponseEntity.ok(workoutService.createWorkout(workoutCreateDTO));
        }
        catch (UserNotFound ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutDTO> getWorkout(long id) {
        return ResponseEntity.ok(workoutService.getWorkoutByWorkoutId(id));
    }

    @GetMapping("/byuser/{id}")
    public ResponseEntity<List<WorkoutDTO>> getWorkoutByUser(@PathVariable UUID id) {
        try{
            return ResponseEntity.ok(workoutService.getWorkoutByUserId(id));

        }catch (UserNotFound ex){
            return  ResponseEntity.notFound().build();
        }
    }


    @GetMapping
    public ResponseEntity<List<WorkoutDTO>> getAllWorkouts() {
        return ResponseEntity.ok(workoutService.getWorkouts());
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutDTO> updateWorkout(@PathVariable long id, @RequestBody WorkoutUpdateDTO workoutUpdateDTO) {
        try{
            return ResponseEntity.ok(workoutService.updateWorkout(id, workoutUpdateDTO));
        }
        catch (UserNotFound ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorkout(long id) {
        try{
            workoutService.deleteWorkoutById(id);
            return ResponseEntity.ok("Workout with id " + id + " deleted");
        }
        catch (WorkoutNotFound ex) {
            return  ResponseEntity.notFound().build();
        }
    }

}

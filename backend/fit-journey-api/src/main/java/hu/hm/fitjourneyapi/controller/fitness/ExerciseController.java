package hu.hm.fitjourneyapi.controller.fitness;

import hu.hm.fitjourneyapi.dto.fitness.excercise.AbstractExerciseDTO;
import hu.hm.fitjourneyapi.dto.fitness.excercise.ExerciseUpdateDTO;
import hu.hm.fitjourneyapi.exception.fitness.ExerciseNotFound;
import hu.hm.fitjourneyapi.services.interfaces.fitness.ExerciseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/exercise")
public class ExerciseController {

    ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PostMapping
    public ResponseEntity<AbstractExerciseDTO> createExercise(@RequestBody AbstractExerciseDTO exerciseDTO) {
        try{
            AbstractExerciseDTO exercise =exerciseService.createExercise(exerciseDTO);
            return ResponseEntity.ok(exercise);
        } catch (NoSuchFieldException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<AbstractExerciseDTO>> getExercises() {
        return ResponseEntity.ok(exerciseService.getExercises());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbstractExerciseDTO> getExercise(@PathVariable long id) {
        try{
            return ResponseEntity.ok(exerciseService.getById(id));
        }
        catch (ExerciseNotFound ex)
        {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/byname/{name}")
    public ResponseEntity<List<AbstractExerciseDTO>> getExercisesByName(@PathVariable String name) {
        return ResponseEntity.ok(exerciseService.getByName(name));
    }

    @GetMapping("/byuser/{id}")
    public ResponseEntity<List<AbstractExerciseDTO>> getExercisesByUser(@PathVariable UUID id) {
        return ResponseEntity.ok(exerciseService.getByUserId(id));
    }

    @GetMapping("/byworkout/{id}")
    public ResponseEntity<List<AbstractExerciseDTO>> getExercisesByWorkout(@PathVariable long id) {
        return ResponseEntity.ok(exerciseService.getByWorkoutId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable long id) {
        try
        {
            exerciseService.deleteExerciseById(id);
            return ResponseEntity.noContent().build();
        }
        catch (ExerciseNotFound ex){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AbstractExerciseDTO> updateExercise(@PathVariable int id,@RequestBody ExerciseUpdateDTO exerciseDTO) {
        try{
            return ResponseEntity.ok(exerciseService.updateExercise(id, exerciseDTO));
        }
        catch (ExerciseNotFound ex)
        {
            return ResponseEntity.notFound().build();
        }
    }
}

package hu.hm.fitjourneyapi.controller.fitness;

import hu.hm.fitjourneyapi.dto.fitness.excercise.AbstractExerciseDTO;
import hu.hm.fitjourneyapi.services.interfaces.fitness.ExerciseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

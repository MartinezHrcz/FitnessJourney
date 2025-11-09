package hu.hm.fitjourneyapi.controller.fitness;

import hu.hm.fitjourneyapi.dto.fitness.exerciseTemplates.DefaultExerciseDTO;
import hu.hm.fitjourneyapi.services.interfaces.fitness.DefaultExerciseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/default/exercise")
public class DefaultExerciseController {
    private final DefaultExerciseService defaultExerciseService;

    public DefaultExerciseController(DefaultExerciseService defaultExerciseService) {
        this.defaultExerciseService = defaultExerciseService;
    }

    @GetMapping
    public ResponseEntity<List<DefaultExerciseDTO>> getDefaultExercises() {
        return ResponseEntity.ok(defaultExerciseService.getDefaultExercises());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DefaultExerciseDTO> getDefaultExercise(@PathVariable long id) {
        return ResponseEntity.ok(defaultExerciseService.getDefaultExercise(id));
    }

    @GetMapping("/byname/{name}")
    public ResponseEntity<List<DefaultExerciseDTO>> getDefaultExercisesByName(@PathVariable String name) {
        return ResponseEntity.ok(defaultExerciseService.getDefaultExercisesByName(name));
    }
}

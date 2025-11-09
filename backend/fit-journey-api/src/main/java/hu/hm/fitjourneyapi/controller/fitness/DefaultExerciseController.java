package hu.hm.fitjourneyapi.controller.fitness;

import hu.hm.fitjourneyapi.dto.fitness.premadeExercises.DefaultExerciseDTO;
import hu.hm.fitjourneyapi.services.interfaces.fitness.DefaultExerciseService;
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
    public List<DefaultExerciseDTO> getDefaultExercises() {
        return defaultExerciseService.getDefaultExercises();
    }

    @GetMapping("/{id}")
    public DefaultExerciseDTO getDefaultExercise(@PathVariable long id) {
        return defaultExerciseService.getDefaultExercise(id);
    }

    @GetMapping("/byname/{name}")
    public  List<DefaultExerciseDTO> getDefaultExercisesByName(@PathVariable String name) {
        return defaultExerciseService.getDefaultExercisesByName(name);
    }
}

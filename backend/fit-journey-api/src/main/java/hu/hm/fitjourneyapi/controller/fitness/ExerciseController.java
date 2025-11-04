package hu.hm.fitjourneyapi.controller.fitness;

import hu.hm.fitjourneyapi.services.interfaces.fitness.ExerciseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exercise")
public class ExerciseController {

    ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }


}

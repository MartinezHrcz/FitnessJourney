package hu.hm.fitjourneyapi.exception.fitness;

public class ExerciseNotFound extends RuntimeException {
    public ExerciseNotFound(String message) {
        super(message);
    }
}

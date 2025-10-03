package hu.hm.fitjourneyapi.exception.fitness;

public class WorkoutNotFound extends RuntimeException {
    public WorkoutNotFound(String message) {
        super(message);
    }
}

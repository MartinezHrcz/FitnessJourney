package hu.hm.fitjourneyapi.exception.fitness.userExceptions;

public class UserNotFound extends RuntimeException {
    public UserNotFound(String message) {
        super(message);
    }
}

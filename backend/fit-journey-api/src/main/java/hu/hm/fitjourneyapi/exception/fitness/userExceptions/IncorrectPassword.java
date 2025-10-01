package hu.hm.fitjourneyapi.exception.fitness.userExceptions;

public class IncorrectPassword extends RuntimeException {
    public IncorrectPassword(String message) {
        super(message);
    }
}

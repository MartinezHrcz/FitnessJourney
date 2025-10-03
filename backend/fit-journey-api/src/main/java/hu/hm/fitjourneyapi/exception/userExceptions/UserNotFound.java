package hu.hm.fitjourneyapi.exception.userExceptions;

public class UserNotFound extends RuntimeException {
    public UserNotFound(String message) {
        super(message);
    }
}

package hu.hm.fitjourneyapi.exception.social.friend;

public class FriendNotFoundException extends RuntimeException {
    public FriendNotFoundException(String message) {
        super(message);
    }
}

package security.errorhandling;

// Homemade exception
public class NotAuthorizedException extends Exception {

    // Constructors
    public NotAuthorizedException(String message) {
        super(message);
    }

    public NotAuthorizedException() {
        super("Could not be authorized");
    }
}

package security.errorhandling;

// Homemade exception
public class AuthenticationException extends Exception {
    // Constructors
    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException() {
        super("Could not be authenticated");
    }
}

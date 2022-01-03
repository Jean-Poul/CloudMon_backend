package errorhandling;

// Homemade exception
public class NotFoundException extends Exception {

    // Constructors
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException() {
        super("Requested item could not be found");
    }
}

package errorhandling;

// Homemade exception
public class NoConnectionException extends Exception {

    // Constructors
    public NoConnectionException(String msg) {
        super(msg);
    }

    public NoConnectionException() {
        super("No connection to the server");
    }

}

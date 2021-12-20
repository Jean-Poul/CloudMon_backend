
package errorhandling;

public class NoConnectionException extends Exception {

    public NoConnectionException(String msg) {
        super(msg);
    }

    public NoConnectionException() {
        super("No connection to the server");
    }

}

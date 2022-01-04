package errorhandling;

public class ExceptionDTO {

    private int code;
    private String message;

    // Constructor
    public ExceptionDTO(int code, String description) {
        this.code = code;
        this.message = description;
    }

    // Getters
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}

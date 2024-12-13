package app.exception;

import java.sql.SQLException;

public class IllegalInputException extends Exception {

    // Constructor with no arguments
    public IllegalInputException() {
        super("Invalid input provided.");
    }

    // Constructor with a custom message
    public IllegalInputException(String message) {
        super(message);
    }

    // Constructor with a custom message and a cause
    public IllegalInputException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor with a cause
    public IllegalInputException(Throwable cause) {
        super(cause);
    }
}

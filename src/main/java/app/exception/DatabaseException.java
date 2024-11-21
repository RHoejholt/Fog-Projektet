package app.exception;

import java.sql.SQLException;

public class DatabaseException extends Exception {
    public DatabaseException(String errorMessage) {
        super(errorMessage);
    }

    public DatabaseException(String errorMessage, SQLException exception) {
        super(errorMessage, exception);
    }
}
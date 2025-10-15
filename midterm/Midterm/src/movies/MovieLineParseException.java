package movies;

/** Unchecked exception for malformed CSV lines. */
public class MovieLineParseException extends RuntimeException {
    public MovieLineParseException(String message) {
        super(message);
    }
    public MovieLineParseException(String message, Throwable cause) {
        super(message, cause);
    }
}

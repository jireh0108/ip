package nova.exceptions;

/**
 * The base exception class for all custom exceptions in Nova.
 * <p>
 * This exception serves as the superclass for all exceptions specific to the
 * Nova application, providing a common type for error handling.
 * </p>
 */
public class NovaException extends RuntimeException {

    /**
     * Constructs a new NovaException with the specified detail message.
     *
     * @param msg The detail message for this exception.
     */
    public NovaException(String msg) {
        super(msg);
    }
}

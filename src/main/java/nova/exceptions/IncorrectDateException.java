package nova.exceptions;

/**
 * Thrown when a date provided by the user is in an invalid format.
 * <p>
 * This exception is raised when Nova cannot parse the date input. The message
 * typically guides the user to enter a valid date format, e.g., "Dec 31 2025"
 * or "31/12/2025".
 * </p>
 */
public class IncorrectDateException extends NovaException {

    /**
     * Constructs a new IncorrectDateException with a default error message.
     */
    public IncorrectDateException() {
        super("Invalid date format, try something like Dec 31 2025 or 31/12/2025!\n");
    }
}

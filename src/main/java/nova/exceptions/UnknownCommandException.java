package nova.exceptions;

/**
 * Thrown when the user enters a command that Nova does not recognize.
 * <p>
 * This exception is raised when the input command does not match any
 * known commands in Nova. The message typically guides the user to type
 * 'help' to see the list of available commands.
 * </p>
 */
public class UnknownCommandException extends NovaException {

    /**
     * Constructs a new UnknownCommandException with a default error message.
     */
    public UnknownCommandException() {
        super("Unknown command... nova doesn't know what to do :(\n"
                + "Type 'help' for list of commands.");
    }
}

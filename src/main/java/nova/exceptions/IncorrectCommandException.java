package nova.exceptions;

import nova.commands.Command;

/**
 * Thrown when a command is used incorrectly in Nova.
 * <p>
 * This exception is raised when the user input does not match the expected format
 * of a {@link Command}. The exception message includes the correct usage format
 * for the command.
 * </p>
 */
public class IncorrectCommandException extends NovaException {

    /**
     * Constructs a new IncorrectCommandException for the given command.
     *
     * @param c The {@link Command} that was used incorrectly.
     */
    public IncorrectCommandException(Command c) {
        super("Incorrect command usage... nova doesn't know what to do...\n"
                + "Make sure this is the format!\n" + "Usage: " + c.getFormat());
    }
}

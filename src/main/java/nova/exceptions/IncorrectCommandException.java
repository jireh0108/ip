package nova.exceptions;

import nova.commands.Command;

public class IncorrectCommandException extends NovaException {
    public IncorrectCommandException(Command c) {
        super("Incorrect command usage... nova doesn't know what to do...\n"
                + "Make sure this is the format!\n" + "Usage: " + c.getFormat());
    }
}

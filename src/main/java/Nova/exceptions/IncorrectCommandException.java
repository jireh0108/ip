package Nova.exceptions;

import Nova.commands.Command;

public class IncorrectCommandException extends NovaException {
    public IncorrectCommandException(Command c) {
        super("Incorrect command usage... Nova doesn't know what to do...\n"
                + "Make sure this is the format!\n" + "Usage: " + c.getFormat());
    }
}

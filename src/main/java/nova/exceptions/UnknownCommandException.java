package nova.exceptions;

public class UnknownCommandException extends NovaException {
    public UnknownCommandException() {
        super("Unknown command... nova doesn't know what to do :(\n"
                + "Type 'help' for list of commands.");
    }
}

package Nova.exceptions;

public class IncorrectDateException extends NovaException {
    public IncorrectDateException() {
        super("Invalid date format, try something like Dec 31 2025 or 31/12/2025!\n");
    }
}

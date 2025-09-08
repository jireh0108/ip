package nova.parser;

import static java.util.Map.entry;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.function.Function;

import nova.commands.Command;
import nova.commands.DeadlineCommand;
import nova.commands.DeleteCommand;
import nova.commands.EventCommand;
import nova.commands.ExitCommand;
import nova.commands.FindCommand;
import nova.commands.HelpCommand;
import nova.commands.ListCommand;
import nova.commands.MarkCommand;
import nova.commands.ScheduleCommand;
import nova.commands.TodoCommand;
import nova.commands.UnmarkCommand;
import nova.exceptions.IncorrectCommandException;
import nova.exceptions.IncorrectDateException;
import nova.exceptions.NovaException;
import nova.exceptions.UnknownCommandException;
import nova.tasks.Deadline;
import nova.tasks.Event;
import nova.tasks.Task;
import nova.tasks.ToDo;

/**
 * Utility class for parsing user input into {@link Command} objects or date/time objects.
 * <p>
 * Provides methods to interpret command strings and date/time strings, throwing
 * appropriate exceptions for invalid input.
 * </p>
 */
public class Parser {

    private static final Map<String, Function<String, Command>> COMMAND_MAP = Map.ofEntries(
            entry("list", args -> new ListCommand()),
            entry("help", args -> new HelpCommand()),
            entry("bye", args -> new ExitCommand()),
            entry("mark", args -> new MarkCommand(parseIndex(args, new MarkCommand(-1)))),
            entry("unmark", args -> new UnmarkCommand(parseIndex(args, new UnmarkCommand(-1)))),
            entry("delete", args -> new DeleteCommand(parseIndex(args, new DeleteCommand(-1)))),
            entry("todo", args -> parseNonEmpty(args, TodoCommand::new, new TodoCommand(""))),
            entry("deadline", args -> parseNonEmpty(args, DeadlineCommand::new, new DeadlineCommand(""))),
            entry("event", args -> parseNonEmpty(args, EventCommand::new, new EventCommand(""))),
            entry("schedule", args -> parseNonEmpty(args, ScheduleCommand::new, new ScheduleCommand(""))),
            entry("find", args -> parseNonEmpty(args, FindCommand::new, new FindCommand("")))
    //add new command here
    );
    /**
     * Parses a user input string into a {@link Command} object.
     * The input string is expected to start with a command keyword (e.g., "todo", "mark"),
     * optionally followed by arguments separated by a space. This method looks up the
     * command in the {@link #COMMAND_MAP} and applies the corresponding constructor/lambda.
     *
     * @param line the raw input string entered by the user
     * @return the {@link Command} object corresponding to the input
     * @throws NovaException if the command is unknown or if the arguments are invalid
     */
    public static Command parse(String line) throws NovaException {
        String[] parts = line.trim().split(" ", 2);
        String commandWord = parts[0].toLowerCase();
        String args = parts.length > 1 ? parts[1] : null;

        Function<String, Command> constructor = COMMAND_MAP.get(commandWord);
        if (constructor == null) {
            throw new UnknownCommandException();
        }

        return constructor.apply(args);
    }
    /**
     * Parses a numeric argument string into a zero-based task index.
     * Validates that the argument is a positive integer. If the argument is null or invalid,
     * throws an {@link IncorrectCommandException} with the provided fallback command.
     *
     * @param arg the argument string to parse
     * @param fallback the command to use in the exception if parsing fails
     * @return the zero-based index represented by the argument
     * @throws NovaException if the argument is invalid
     */
    private static int parseIndex(String arg, Command fallback) throws NovaException {
        if (arg == null || !arg.matches("\\d+")) {
            throw new IncorrectCommandException(fallback);
        }
        return Integer.parseInt(arg) - 1;
    }
    /**
     * Parses a non-empty string argument and applies a factory to produce a {@link Command}.
     * Validates that the argument is not null or blank. If it is invalid, throws an
     * {@link IncorrectCommandException} using the provided fallback command.
     *
     * @param arg the argument string to parse
     * @param factory a function that creates a {@link Command} from a valid argument
     * @param fallback the command to use in the exception if the argument is invalid
     * @return the {@link Command} produced by the factory
     * @throws NovaException if the argument is null or blank
     */
    private static Command parseNonEmpty(String arg, Function<String, Command> factory, Command fallback)
            throws NovaException {
        if (arg == null || arg.isBlank()) {
            throw new IncorrectCommandException(fallback);
        }
        return factory.apply(arg);
    }
    /**
     * Parses through a String taken from storage text to
     * either return a Command or throw an error for unreadable input.
     *
     * @param line Input from storage.
     * @return Task based on input.
     */
    public static Task parseStorageTaskString(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            System.err.println("Invalid task line: " + line);
            return null;
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        Task task = null;

        switch (type) {
        case "T" -> task = new ToDo(parts[2]);
        case "D" -> {
            LocalDateTime deadline = parseDateTime(parts[3]);
            if (deadline != null) {
                task = new Deadline(parts[2], deadline);
            }
        }
        case "E" -> {
            LocalDateTime from = parseDateTime(parts[3]);
            LocalDateTime to = parseDateTime(parts[4]);
            if (from != null && to != null) {
                task = new Event(parts[2], from, to);
            }
        }
        default -> {
            System.err.println("Warning: unknown task type");
            assert false : "Unexpected task type: " + type;
        }
        }

        if (task != null && isDone) {
            task.mark();
        }
        return task;
    }

    /**
     * Parses through user's input of date/time to either return
     * a LocalDateTime object or throw an error for unreadable input.
     *
     * @param dateStr User's input for date/time.
     * @return LocalDateTime object.
     * @throws IncorrectDateException if user's input is unreadable.
     */
    public static LocalDateTime parseDateTime(String dateStr) throws IncorrectDateException {
        // try ISO date-time and ISO date first
        try {
            return LocalDateTime.parse(dateStr.trim(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException ignored) {
            //keep trying
        }

        try {
            LocalDate date = LocalDate.parse(dateStr.trim(), DateTimeFormatter.ISO_LOCAL_DATE);
            return date.atStartOfDay();
        } catch (DateTimeParseException ignored) {
            // keep trying
        }

        // fallback to custom formats
        LocalDateTime result = tryParseWithFormats(dateStr,
                DateTimeFormatter.ofPattern("d/M/yyyy HHmm"),
                DateTimeFormatter.ofPattern("d/M/yyyy"),
                DateTimeFormatter.ofPattern("d MMM yyyy HHmm"),
                DateTimeFormatter.ofPattern("d MMM yyyy"),
                DateTimeFormatter.ofPattern("MMM d yyyy HHmm"),
                DateTimeFormatter.ofPattern("MMM d yyyy")
        );

        if (result != null) {
            return result;
        }
        throw new IncorrectDateException();
    }

    /**
     * Cleaner way of parsing with different date formats.
     * @param dateStr date input
     * @param formatters different date formats
     * @return LocalDateTime
     */
    private static LocalDateTime tryParseWithFormats(String dateStr, DateTimeFormatter... formatters) {
        for (DateTimeFormatter fmt : formatters) {
            try {
                return LocalDateTime.parse(dateStr.trim(), fmt);
            } catch (DateTimeParseException ignored) {
                try {
                    LocalDate date = LocalDate.parse(dateStr.trim(), fmt);
                    return date.atStartOfDay();
                } catch (DateTimeParseException ignored2) {
                    // keep trying
                }
            }
        }
        return null;
    }
}

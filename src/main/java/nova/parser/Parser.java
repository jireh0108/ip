package nova.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
    /**
     * Parses through user's input to either return a Command or throw an error for unreadable input.
     *
     * @param line User's input.
     * @return Command based on user's input.
     * @throws NovaException if user's input is unreadable.
     */
    public static Command parse(String line) throws NovaException {
        String[] parts = line.trim().split(" ", 2);
        String commandWord = parts[0].toLowerCase();

        switch (commandWord) {
        case "list":
            return new ListCommand();

        case "mark":
            if (parts.length < 2 || !parts[1].matches("\\d+")) {
                throw new IncorrectCommandException(new MarkCommand(-1));
            }
            int markIndex = Integer.parseInt(parts[1]) - 1;
            return new MarkCommand(markIndex);

        case "unmark":
            if (parts.length < 2 || !parts[1].matches("\\d+")) {
                throw new IncorrectCommandException(new UnmarkCommand(-1));
            }
            int unmarkIndex = Integer.parseInt(parts[1]) - 1;
            return new UnmarkCommand(unmarkIndex);

        case "todo":
            if (parts.length < 2 || parts[1].isBlank()) {
                throw new IncorrectCommandException(new TodoCommand(""));
            }
            return new TodoCommand(parts[1]);

        case "deadline":
            if (parts.length < 2 || parts[1].isBlank()) {
                throw new IncorrectCommandException(new DeadlineCommand(""));
            }
            return new DeadlineCommand(parts[1]);

        case "event":
            if (parts.length < 2 || parts[1].isBlank()) {
                throw new IncorrectCommandException(new EventCommand(""));
            }
            return new EventCommand(parts[1]);

        case "delete":
            if (parts.length < 2 || !parts[1].matches("\\d+")) {
                throw new IncorrectCommandException(new DeleteCommand(-1));
            }
            int deleteIndex = Integer.parseInt(parts[1]) - 1;
            return new DeleteCommand(deleteIndex);

        case "schedule":
            if (parts.length < 2 || parts[1].isBlank()) {
                throw new IncorrectCommandException(new ScheduleCommand(""));
            }
            return new ScheduleCommand(parts[1]);

        case "find":
            if (parts.length < 2 || parts[1].isBlank()) {
                throw new IncorrectCommandException(new FindCommand(""));
            }
            return new FindCommand(parts[1]);

        case "help":
            return new HelpCommand();

        case "bye":
            return new ExitCommand();

        default:
            throw new UnknownCommandException();
        }
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

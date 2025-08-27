package nova.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import nova.commands.Command;
import nova.commands.DeadlineCommand;
import nova.commands.DeleteCommand;
import nova.commands.EventCommand;
import nova.commands.ExitCommand;
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

        case "help":
            return new HelpCommand();

        case "bye":
            return new ExitCommand();

        default:
            throw new UnknownCommandException();
        }
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
        // try ISO date and time
        try {
            return LocalDateTime.parse(dateStr.trim(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException ignored) {
        }

        // try ISO date only
        try {
            LocalDate date = LocalDate.parse(dateStr.trim(), DateTimeFormatter.ISO_LOCAL_DATE);
            return date.atStartOfDay();
        } catch (DateTimeParseException ignored) {
        }

        // try custom formats
        DateTimeFormatter[] customFormats = {
                DateTimeFormatter.ofPattern("d/M/yyyy HHmm"),
                DateTimeFormatter.ofPattern("d/M/yyyy"),
                DateTimeFormatter.ofPattern("d MMM yyyy HHmm"),
                DateTimeFormatter.ofPattern("d MMM yyyy"),
                DateTimeFormatter.ofPattern("MMM d yyyy HHmm"),
                DateTimeFormatter.ofPattern("MMM d yyyy"),
        };

        for (DateTimeFormatter fmt : customFormats) {
            try {
                return LocalDateTime.parse(dateStr.trim(), fmt);
            } catch (DateTimeParseException ignored) {
                try {
                    LocalDate date = LocalDate.parse(dateStr.trim(), fmt);
                    return date.atStartOfDay();
                } catch (DateTimeParseException ignored2) {
                }
            }
        }

        throw new IncorrectDateException();
    }
}

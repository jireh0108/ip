package nova.commands;

import java.time.LocalDateTime;

import nova.parser.Parser;
import nova.storage.Storage;
import nova.tasks.Event;
import nova.tasks.Task;
import nova.tasks.TaskList;
import nova.ui.Ui;

/**
 * Represents a command to add an event task to Nova's task list.
 * <p>
 * The command parses the user input to extract the event description,
 * start date/time, and end date/time. It then creates an {@link Event} task,
 * adds it to the {@link TaskList}, persists the updated list to {@link Storage},
 * and displays confirmation messages via {@link Ui}.
 * </p>
 */
public class EventCommand extends Command {
    /**
     * Description of the event.
     */
    private final String description;

    /**
     * Start date/time of the event.
     */
    private final String from;

    /**
     * End date/time of the event.
     */
    private final String to;

    /**
     * Constructs a new EventCommand with the given user input.
     *
     * @param input The raw input string in the format "description /from start /to end"
     */
    public EventCommand(String input) {
        String[] parts = input.split(" /from ", 2);
        this.description = parts[0];
        if (parts.length < 2 || !parts[1].contains(" /to ")) {
            this.from = null;
            this.to = null;
        } else {
            String[] timings = parts[1].split(" /to ", 2);
            this.from = timings[0];
            this.to = timings[1];
        }
    }

    /**
     * Executes the command: adds an event task to the task list and updates storage.
     * Displays appropriate messages to the user.
     *
     * @param tasks   The current {@link TaskList} of Nova.
     * @param ui      The current {@link Ui} instance for user interaction.
     * @param storage The current {@link Storage} instance for persisting tasks.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        if (from == null || to == null) {
            return "Usage: event <description> /from <start> /to <end>";
        }

        LocalDateTime start = Parser.parseDateTime(from);
        LocalDateTime end = Parser.parseDateTime(to);
        if (start == null || end == null) {
            return "";
        }

        Task curr = new Event(description, start, end);
        tasks.add(curr);
        storage.write(tasks);
        return "Got it. I've added this task:\n  " + curr
                        + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Returns the expected input format for this command.
     *
     * @return A string representing the input format.
     */
    @Override
    public String getFormat() {
        return "event <description> /from <date> /to <date>";
    }
}

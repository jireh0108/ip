package nova.commands;

import java.time.LocalDateTime;

import nova.parser.Parser;
import nova.storage.Storage;
import nova.tasks.Deadline;
import nova.tasks.Task;
import nova.tasks.TaskList;
import nova.ui.Ui;

/**
 * Represents a command to add a deadline task to Nova's task list.
 * <p>
 * The command parses the user input to extract the task description and the
 * deadline date/time, creates a {@link Deadline} task, adds it to the {@link TaskList},
 * persists the updated list to {@link Storage}, and displays confirmation messages via {@link Ui}.
 * </p>
 */
public class DeadlineCommand extends Command {
    private final String description;
    private final String by;

    /**
     * Constructs a new DeadlineCommand with the given user input.
     *
     * @param input The raw input string in the format "description /by date"
     */
    public DeadlineCommand(String input) {
        String[] parts = input.split(" /by ", 2);
        this.description = parts[0];
        this.by = (parts.length > 1) ? parts[1] : null;
    }

    /**
     * Executes the command: adds a deadline task to the task list and updates storage.
     * Displays appropriate messages to the user.
     *
     * @param tasks   The current {@link TaskList} of Nova.
     * @param ui      The current {@link Ui} instance for user interaction.
     * @param storage The current {@link Storage} instance for persisting tasks.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (by == null) {
            ui.showText("Usage: deadline <description> /by <deadline>");
            return;
        }
        LocalDateTime deadline = Parser.parseDateTime(by);
        if (deadline == null) {
            return;
        }

        Task curr = new Deadline(description, deadline);
        tasks.add(curr);
        storage.write(tasks);
        ui.showText("Got it. I've added this task:\n  " + curr
                + "\nNow you have " + tasks.size() + " tasks in the list.");
    }

    /**
     * Returns the expected input format for this command.
     *
     * @return A string representing the input format.
     */
    @Override
    public String getFormat() {
        return "deadline <description> /by <date>";
    }
}

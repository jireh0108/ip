package nova.commands;

import nova.storage.Storage;
import nova.tasks.Task;
import nova.tasks.TaskList;
import nova.tasks.ToDo;
import nova.ui.Ui;

/**
 * Represents a command to add a ToDo task to Nova's task list.
 * <p>
 * The command creates a new {@link ToDo} task with the given description,
 * adds it to the {@link TaskList}, saves the updated list to {@link Storage},
 * and displays a confirmation message via {@link Ui}.
 * </p>
 */
public class TodoCommand extends Command {

    /**
     * The description of the ToDo task.
     */
    private final String description;

    /**
     * Constructs a new TodoCommand with the given task description.
     *
     * @param description The description of the ToDo task.
     */
    public TodoCommand(String description) {
        this.description = description;
    }

    /**
     * Executes the todo command: adds a new ToDo task, updates storage,
     * and displays a confirmation message.
     *
     * @param tasks   The current {@link TaskList} of Nova.
     * @param ui      The {@link Ui} instance for displaying messages.
     * @param storage The {@link Storage} instance for persisting the updated task list.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task curr = new ToDo(description);
        tasks.add(curr);
        storage.write(tasks);
        ui.showText("Got it. I've added this task:\n  " + curr
                + "\nNow you have " + tasks.size() + " tasks in the list.");
    }

    /**
     * Returns the expected input format for this command.
     *
     * @return A string representing the command format.
     */
    @Override
    public String getFormat() {
        return "todo <description>";
    }
}

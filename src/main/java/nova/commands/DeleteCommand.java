package nova.commands;

import nova.storage.Storage;
import nova.tasks.Task;
import nova.tasks.TaskList;
import nova.ui.Ui;

/**
 * Represents a command to delete a task from Nova's task list.
 * <p>
 * The command removes the task at the specified index from the {@link TaskList},
 * updates the {@link Storage}, and displays a confirmation message via {@link Ui}.
 * </p>
 */
public class DeleteCommand extends Command {
    /**
     * The index of the task to be deleted (0-based).
     */
    private final int index;

    /**
     * Constructs a new DeleteCommand for the task at the given index.
     *
     * @param index the 0-based index of the task to remove
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the command: deletes the task at the specified index and updates storage.
     * Displays appropriate messages to the user.
     *
     * @param tasks   The current {@link TaskList} of Nova.
     * @param ui      The current {@link Ui} instance for user interaction.
     * @param storage The current {@link Storage} instance for persisting tasks.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        if (index < 0 || index >= tasks.size()) {
            return "Invalid task number!";
        }

        Task curr = tasks.get(index);
        tasks.remove(index);
        storage.write(tasks);
        return "Noted. I've removed this task:\n  " + curr
                        + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Returns the expected input format for this command.
     *
     * @return A string representing the input format.
     */
    @Override
    public String getFormat() {
        return "delete <task number>";
    }
}

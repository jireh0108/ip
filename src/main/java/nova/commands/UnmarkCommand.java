package nova.commands;

import nova.storage.Storage;
import nova.tasks.Task;
import nova.tasks.TaskList;
import nova.ui.Ui;

/**
 * Represents a command to unmark a task in Nova's task list as not done.
 * <p>
 * The command unmarks the task at the specified index in the {@link TaskList}.
 * If the task is already unmarked, it notifies the user. The updated task list
 * is saved to {@link Storage} and a confirmation message is displayed via {@link Ui}.
 * </p>
 */
public class UnmarkCommand extends Command {

    /**
     * The index of the task to unmark (0-based).
     */
    private final int index;

    /**
     * Constructs a new UnmarkCommand for the task at the given index.
     *
     * @param index The 0-based index of the task to unmark.
     */
    public UnmarkCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the unmark command: unmarks the task, updates storage,
     * and displays a confirmation message.
     *
     * @param tasks   The current {@link TaskList} of Nova.
     * @param ui      The {@link Ui} instance for displaying messages.
     * @param storage The {@link Storage} instance for persisting the updated task list.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        if (index < 0 || index >= tasks.size()) {
            return "Invalid task number!";
        }

        Task curr = tasks.get(index);
        if (curr.getStatus()) {
            curr.unmark();
            storage.write(tasks);
            return "OK, I've marked this task as not done yet:\n  " + curr;
        } else {
            return "The task is already unmarked!";
        }
    }

    /**
     * Returns the expected input format for this command.
     *
     * @return A string representing the command format.
     */
    @Override
    public String getFormat() {
        return "unmark <task number>";
    }
}

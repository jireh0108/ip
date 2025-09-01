package nova.commands;

import nova.storage.Storage;
import nova.tasks.Task;
import nova.tasks.TaskList;
import nova.ui.Ui;

/**
 * Represents a command to mark a task in Nova's task list as completed.
 * <p>
 * The command marks the task at the specified index in the {@link TaskList} as done.
 * If the task is already marked, it notifies the user. The updated list is saved
 * to {@link Storage} and a confirmation message is displayed via {@link Ui}.
 * </p>
 */
public class MarkCommand extends Command {

    /**
     * The index of the task to mark as done (0-based).
     */
    private final int index;

    /**
     * Constructs a new MarkCommand for the task at the given index.
     *
     * @param index The 0-based index of the task to mark as done.
     */
    public MarkCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the mark command: marks the task as done, updates storage,
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
        if (!curr.getStatus()) {
            curr.mark();
            storage.write(tasks);
            return "Nice! I've marked this task as done:\n  " + curr;
        } else {
            return "The task is already marked!";
        }
    }

    /**
     * Returns the expected input format for this command.
     *
     * @return A string representing the command format.
     */
    @Override
    public String getFormat() {
        return "mark <task number>";
    }
}

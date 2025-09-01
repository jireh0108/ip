package nova.commands;

import nova.storage.Storage;
import nova.tasks.TaskList;
import nova.ui.Ui;

/**
 * Represents a command that lists all tasks in Nova's task list.
 * <p>
 * When executed, this command displays all tasks in the provided {@link TaskList}.
 * If the task list is empty, it shows a message indicating that there are no tasks.
 * </p>
 */
public class ListCommand extends Command {

    /**
     * Executes the list command by displaying all tasks in the task list.
     *
     * @param tasks   The current {@link TaskList} of Nova.
     * @param ui      The {@link Ui} instance used to display messages.
     * @param storage The {@link Storage} instance (not used by this command).
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        if (tasks.isEmpty()) {
            return "There are no tasks! Try \"help\" for commands!";
        }
        return "Here are the tasks in your list:\n" + tasks;
    }

    /**
     * Returns the expected input format for this command.
     *
     * @return A string representing the command format.
     */
    @Override
    public String getFormat() {
        return "list";
    }
}

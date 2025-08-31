package nova.commands;

import nova.storage.Storage;
import nova.tasks.TaskList;
import nova.ui.Ui;
/**
 * Represents an abstract command in the Nova application.
 * <p>
 * All specific commands (e.g., DeadlineCommand, TodoCommand) should extend this class
 * and implement the {@link #execute(TaskList, Ui, Storage)} method to define their behavior.
 * This class also provides a default {@link #isExit()} method, which can be overridden
 * by commands that should terminate the program (e.g., ExitCommand).
 * </p>
 */
public abstract class Command {
    public Command() {}
    /**
     * Executes specific functions of the child classes.
     *
     * @param tasks   Current Tasklist of the Nova instance.
     * @param ui      Current Ui of the Nova instance.
     * @param storage Current Storage of the Nova instance.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage);

    /**
     * Returns boolean flag for exiting the program.
     *
     * @return Only returns true for child class ExitCommand, else return false.
     */
    public boolean isExit() {
        return false;
    }

    public abstract String getFormat();
}

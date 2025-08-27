package nova.commands;

import nova.storage.Storage;
import nova.tasks.TaskList;
import nova.ui.Ui;

public abstract class Command {
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

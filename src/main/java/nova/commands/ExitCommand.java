package nova.commands;

import nova.storage.Storage;
import nova.tasks.TaskList;
import nova.ui.Ui;

/**
 * Represents the command to exit the Nova application.
 * <p>
 * When executed, this command displays a goodbye message to the user
 * and signals the program to terminate by returning {@code true} from {@link #isExit()}.
 * </p>
 */
public class ExitCommand extends Command {

    /**
     * Executes the exit command by displaying a farewell message to the user.
     *
     * @param tasks   The current {@link TaskList} (not modified by this command).
     * @param ui      The {@link Ui} instance used to display messages.
     * @param storage The {@link Storage} instance (not modified by this command).
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return "Bye! Hope to see you again soon!";
    }

    /**
     * Indicates that this command will exit the program.
     *
     * @return {@code true} to signal program termination.
     */
    @Override
    public boolean isExit() {
        return true;
    }

    /**
     * Returns the expected input format for this command.
     *
     * @return The string "bye" representing the command format.
     */
    @Override
    public String getFormat() {
        return "bye";
    }
}

package nova.commands;

import nova.storage.Storage;
import nova.tasks.TaskList;
import nova.ui.Ui;

/**
 * Represents a command that displays all available commands in the Nova application.
 * <p>
 * When executed, this command prints a list of commands and their expected input formats
 * to help the user navigate and use Nova effectively.
 * </p>
 */
public class HelpCommand extends Command {

    /**
     * Executes the help command by displaying the list of available commands to the user.
     *
     * @param tasks   The current {@link TaskList} (not modified by this command).
     * @param ui      The {@link Ui} instance used to display messages.
     * @param storage The {@link Storage} instance (not used by this command).
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showText("""
                Here are the available commands:
                  todo <description>
                  deadline <description> /by <date>
                  event <description> /from <date> /to <date>
                  list
                  schedule <date>
                  mark <task number>
                  unmark <task number>
                  delete <task number>
                  help
                  bye""");
    }

    /**
     * Returns the expected input format for this command.
     *
     * @return A string representing the command format.
     */
    @Override
    public String getFormat() {
        return "help";
    }
}

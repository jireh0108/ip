package nova.commands;

import nova.storage.Storage;
import nova.tasks.Task;
import nova.tasks.TaskList;
import nova.ui.Ui;

/**
 * Represents a command to find tasks in Nova's task list that match a given search term.
 * <p>
 * The command searches through the provided {@link TaskList} and collects tasks whose
 * descriptions contain the specified search term. The matching tasks are displayed
 * to the user via {@link Ui}.
 * </p>
 */
public class FindCommand extends Command {

    /**
     * The list of tasks that match the search term.
     */
    protected final TaskList results;

    /**
     * The term to search for in task descriptions.
     */
    protected String searchTerm;

    /**
     * Constructs a new FindCommand with the given search term.
     *
     * @param input The search term used to find matching tasks.
     */
    public FindCommand(String input) {
        this.results = new TaskList();
        this.searchTerm = input;
    }

    /**
     * Executes the find command: searches the given task list for tasks whose
     * descriptions contain the search term and displays the results.
     *
     * @param tasks   The current {@link TaskList} of Nova.
     * @param ui      The {@link Ui} instance for displaying messages.
     * @param storage The {@link Storage} instance (not used in this command).
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        for (Task task : tasks) {
            if (task.getDescription().contains(this.searchTerm)) {
                results.add(task);
            }
        }
        ui.showText("Here are the matching tasks in your list:\n" + results);
    }

    /**
     * Returns the expected input format for this command.
     *
     * @return A string representing the command format.
     */
    @Override
    public String getFormat() {
        return "find <search term>";
    }
}

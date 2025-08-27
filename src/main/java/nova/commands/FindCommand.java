package nova.commands;

import nova.storage.Storage;
import nova.tasks.Task;
import nova.tasks.TaskList;
import nova.ui.Ui;

public class FindCommand extends Command{
    protected final TaskList results;
    protected String searchTerm;

    public FindCommand(String input) {
        this.results = new TaskList();
        this.searchTerm = input;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        for (Task task : tasks) {
            if (task.getDescription().contains(this.searchTerm)) {
                results.add(task);
            }
        }
        ui.showText("Here are the matching tasks in your list:\n" + results);
    }

    @Override
    public String getFormat() {
        return "find <search term>";
    }
}

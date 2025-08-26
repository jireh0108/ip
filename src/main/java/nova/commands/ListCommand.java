package nova.commands;

import nova.storage.Storage;
import nova.tasks.TaskList;
import nova.ui.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (tasks.isEmpty()) {
            ui.showText("There are no tasks! Try \"help\" for commands!");
            return;
        }
        ui.showText("Here are the tasks in your list:\n" + tasks);
    }

    @Override
    public String getFormat() {
        return "list";
    }
}

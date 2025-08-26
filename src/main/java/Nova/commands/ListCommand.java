package Nova.commands;

import Nova.storage.Storage;
import Nova.tasks.TaskList;
import Nova.ui.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (tasks.isEmpty()) {
            ui.showText("There are no tasks! Try \"help\" for commands!");
            return;
        }
        StringBuilder taskString = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            taskString.append("  ").append(i + 1).append(".").append(tasks.get(i)).append("\n");
        }
        ui.showText("Here are the tasks in your list:" + taskString);
    }

    @Override
    public String getFormat() {
        return "list";
    }
}

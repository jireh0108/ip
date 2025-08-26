package Nova.commands;

import Nova.storage.Storage;
import Nova.tasks.TaskList;
import Nova.ui.Ui;

public class ExitCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showText("Bye! Hope to see you again soon!");
    }

    @Override
    public boolean isExit() {
        return true;
    }

    @Override
    public String getFormat() {
        return "bye";
    }
}


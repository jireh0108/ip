package Nova.commands;

import Nova.storage.Storage;
import Nova.tasks.TaskList;
import Nova.ui.Ui;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage);

    public boolean isExit() {
        return false;
    }

    public abstract String getFormat();
}

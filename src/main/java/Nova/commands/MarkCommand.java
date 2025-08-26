package Nova.commands;

import Nova.tasks.Task;
import Nova.tasks.TaskList;
import Nova.storage.Storage;
import Nova.ui.Ui;

public class MarkCommand extends Command {
    private final int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (index < 0 || index >= tasks.size()) {
            ui.showText("Invalid task number!");
            return;
        }
        Task curr = tasks.get(index);
        if (!curr.getStatus()) {
            curr.mark();
            storage.write(tasks);
            ui.showText("Nice! I've marked this task as done:\n  " + curr);
        } else {
            ui.showText("The task is already marked!");
        }
    }

    @Override
    public String getFormat() {
        return "mark <task number>";
    }
}


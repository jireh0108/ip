package nova.commands;

import nova.storage.Storage;
import nova.tasks.Task;
import nova.tasks.TaskList;
import nova.ui.Ui;

public class UnmarkCommand extends Command {
    private final int index;

    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (index < 0 || index >= tasks.size()) {
            ui.showText("Invalid task number!");
            return;
        }
        Task curr = tasks.get(index);
        if (curr.getStatus()) {
            curr.unmark();
            storage.write(tasks);
            ui.showText("OK, I've marked this task as not done yet:\n  " + curr);
        } else {
            ui.showText("The task is already unmarked!");
        }
    }

    @Override
    public String getFormat() {
        return "unmark <task number>";
    }
}

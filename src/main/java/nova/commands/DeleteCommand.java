package nova.commands;

import nova.storage.Storage;
import nova.tasks.Task;
import nova.tasks.TaskList;
import nova.ui.Ui;

public class DeleteCommand extends Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (index < 0 || index >= tasks.size()) {
            ui.showText("Invalid task number!");
            return;
        }
        Task curr = tasks.get(index);
        tasks.remove(index);
        storage.write(tasks);
        ui.showText("Noted. I've removed this task:\n  " + curr +
                "\nNow you have " + tasks.size() + " tasks in the list.");
    }

    @Override
    public String getFormat() {
        return "delete <task number>";
    }
}


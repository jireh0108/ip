package Nova.commands;

import Nova.storage.Storage;
import Nova.tasks.Task;
import Nova.tasks.TaskList;
import Nova.tasks.ToDo;
import Nova.ui.Ui;

public class TodoCommand extends Command {
    private final String description;

    public TodoCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task curr = new ToDo(description);
        tasks.add(curr);
        storage.write(tasks);
        ui.showText("Got it. I've added this task:\n  " + curr +
                "\nNow you have " + tasks.size() + " tasks in the list.");
    }

    @Override
    public String getFormat() {
        return "todo <description>";
    }
}

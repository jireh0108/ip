package Nova.commands;

import Nova.parser.Parser;
import Nova.storage.Storage;
import Nova.tasks.Deadline;
import Nova.tasks.Task;
import Nova.tasks.TaskList;
import Nova.ui.Ui;
import java.time.LocalDateTime;

public class DeadlineCommand extends Command {
    private final String description;
    private final String by;

    public DeadlineCommand(String input) {
        String[] parts = input.split(" /by ", 2);
        this.description = parts[0];
        this.by = (parts.length > 1) ? parts[1] : null;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (by == null) {
            ui.showText("Usage: deadline <description> /by <deadline>");
            return;
        }
        LocalDateTime deadline = Parser.parseDateTime(by);
        if (deadline == null) return;

        Task curr = new Deadline(description, deadline);
        tasks.add(curr);
        storage.write(tasks);
        ui.showText("Got it. I've added this task:\n  " + curr +
                "\nNow you have " + tasks.size() + " tasks in the list.");
    }

    @Override
    public String getFormat() {
        return "deadline <description> /by <date>";
    }
}


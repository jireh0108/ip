package Nova.commands;

import Nova.storage.Storage;
import Nova.parser.Parser;
import Nova.tasks.Event;
import Nova.tasks.TaskList;
import Nova.tasks.Task;
import Nova.ui.Ui;
import java.time.LocalDateTime;

public class EventCommand extends Command {
    private final String description;
    private final String from;
    private final String to;

    public EventCommand(String input) {
        String[] parts = input.split(" /from ", 2);
        this.description = parts[0];
        if (parts.length < 2 || !parts[1].contains(" /to ")) {
            this.from = null;
            this.to = null;
        } else {
            String[] timings = parts[1].split(" /to ", 2);
            this.from = timings[0];
            this.to = timings[1];
        }
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (from == null || to == null) {
            ui.showText("Usage: event <description> /from <start> /to <end>");
            return;
        }
        LocalDateTime start = Parser.parseDateTime(from);
        LocalDateTime end = Parser.parseDateTime(to);
        if (start == null || end == null) return;

        Task curr = new Event(description, start, end);
        tasks.add(curr);
        storage.write(tasks);
        ui.showText("Got it. I've added this task:\n  " + curr +
                "\nNow you have " + tasks.size() + " tasks in the list.");
    }

    @Override
    public String getFormat() {
        return "event <description> /from <date> /to <date>";
    }
}


package nova.commands;

import java.time.LocalDate;
import java.time.LocalDateTime;

import nova.parser.Parser;
import nova.storage.Storage;
import nova.tasks.Deadline;
import nova.tasks.Event;
import nova.tasks.Task;
import nova.tasks.TaskList;
import nova.ui.Ui;

public class ScheduleCommand extends Command {
    private final String dateStr;

    public ScheduleCommand(String dateStr) {
        this.dateStr = dateStr;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        LocalDateTime parsedDateTime = Parser.parseDateTime(dateStr);
        if (parsedDateTime == null) return;

        LocalDate queryDate = parsedDateTime.toLocalDate();
        StringBuilder result = new StringBuilder();
        for (Task task : tasks) {
            if (task instanceof Deadline d) {
                if (d.getBy().toLocalDate().equals(queryDate)) {
                    result.append(d).append("\n");
                }
            } else if (task instanceof Event e) {
                LocalDate fromDate = e.getFrom().toLocalDate();
                LocalDate toDate = e.getTo().toLocalDate();
                if (!queryDate.isBefore(fromDate) && !queryDate.isAfter(toDate)) {
                    result.append(e).append("\n");
                }
            }
        }

        if (result.isEmpty()) {
            ui.showText("No deadlines or events found on " + queryDate);
        } else {
            ui.showText("Scheduled tasks on " + queryDate + ":\n" + result);
        }
    }

    @Override
    public String getFormat() {
        return "schedule <date>";
    }
}

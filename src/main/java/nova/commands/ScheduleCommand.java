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

/**
 * Represents a command that lists all tasks scheduled on a specific date.
 * <p>
 * The command searches the {@link TaskList} for {@link Deadline} and {@link Event} tasks
 * that occur on the specified date. It displays matching tasks via {@link Ui}.
 * </p>
 */
public class ScheduleCommand extends Command {

    /**
     * The date string provided by the user to query tasks.
     */
    private final String dateStr;

    /**
     * Constructs a new ScheduleCommand with the given date string.
     *
     * @param dateStr The date to query scheduled tasks, in a format parsable by {@link Parser#parseDateTime}.
     */
    public ScheduleCommand(String dateStr) {
        this.dateStr = dateStr;
    }

    /**
     * Executes the schedule command: finds all deadlines and events on the specified date
     * and displays them via the {@link Ui} instance.
     *
     * @param tasks   The current {@link TaskList} of Nova.
     * @param ui      The {@link Ui} instance for displaying messages.
     * @param storage The {@link Storage} instance (not used in this command).
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        LocalDateTime parsedDateTime = Parser.parseDateTime(dateStr);
        if (parsedDateTime == null) {
            return;
        }

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

    /**
     * Returns the expected input format for this command.
     *
     * @return A string representing the command format.
     */
    @Override
    public String getFormat() {
        return "schedule <date>";
    }
}

package nova.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a specific deadline.
 * This class extends the Task class and adds functionality for handling
 * deadline-specific information including the due date and time.
 *
 * @see Task
 */
public class Deadline extends Task {

    protected LocalDateTime by;

    /**
     * Constructs a new Deadline task with the specified description and due date/time.
     *
     * @param description the text description of the task
     * @param by the date and time when the task is due, cannot be null
     * @throws NullPointerException if the description or by parameter is null
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the due date and time of this deadline task.
     *
     * @return the LocalDateTime object representing when this task is due
     */
    public LocalDateTime getBy() {
        return this.by;
    }

    /**
     * Returns a string representation of the Deadline task.
     * The format includes the task type identifier [D], the task description,
     * and the formatted due date/time in the pattern "MMM d yyyy HHmm".
     *
     * @return a formatted string representation of the deadline task
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("MMM d yyyy HHmm")) + ")";
    }
}

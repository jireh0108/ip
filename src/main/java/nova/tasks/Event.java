package nova.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task with a specific start and end time.
 * This class extends the Task class and adds functionality for handling
 * event-specific information including the start and end date/time.
 *
 * @see Task
 */
public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    /**
     * Constructs a new Event task with the specified description, start time, and end time.
     *
     * @param description the text description of the event
     * @param from the date and time when the event starts, cannot be null
     * @param to the date and time when the event ends, cannot be null
     * @throws NullPointerException if any parameter is null
     * @throws IllegalArgumentException if the end time is before the start time
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the start date and time of this event.
     *
     * @return the LocalDateTime object representing when this event starts
     */
    public LocalDateTime getFrom() {
        return this.from;
    }

    /**
     * Returns the end date and time of this event.
     *
     * @return the LocalDateTime object representing when this event ends
     */
    public LocalDateTime getTo() {
        return this.to;
    }

    /**
     * Returns a string representation of the Event task.
     * The format includes the task type identifier [E], the task description,
     * and the formatted start and end date/time in the pattern "MMM d yyyy HHmm".
     *
     * @return a formatted string representation of the event task
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: "
                + from.format(DateTimeFormatter.ofPattern("MMM d yyyy HHmm")) + " to: "
                + to.format(DateTimeFormatter.ofPattern("MMM d yyyy HHmm")) + ")";
    }
}
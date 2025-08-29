package nova.tasks;

/**
 * Represents a simple todo task without any date/time constraints.
 * This class extends the Task class and provides a basic implementation
 * for tasks that only require a description without specific timing information.
 *
 * @see Task
 */
public class ToDo extends Task {

    /**
     * Constructs a new ToDo task with the specified description.
     *
     * @param description the text description of the todo task, cannot be null
     * @throws NullPointerException if the description is null
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of the ToDo task.
     * The format includes the task type identifier [T] followed by
     * the string representation from the parent Task class.
     *
     * @return a formatted string representation of the todo task
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
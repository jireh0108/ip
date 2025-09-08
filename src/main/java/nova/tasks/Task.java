package nova.tasks;
/**
 * Represents a task to be completed by the user.
 */
public class Task {
    /**
     * Description of the task as a String
     */
    protected String description;
    /**
     * Status of completion of the task
     */
    protected boolean isDone;
    /**
     * Description of the task
     */
    public Task(String description) {
        assert description != null : "Task description must not be null";
        assert !description.trim().isEmpty() : "Task description must not be empty";
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public boolean getStatus() {
        return isDone;
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * Marks the task as completed
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Marks the task as uncompleted
     */
    public void unmark() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }
}

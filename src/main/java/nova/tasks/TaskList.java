package nova.tasks;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents a collection of tasks and provides operations for managing them.
 * This class implements the Iterable interface to allow iteration over tasks
 * and provides methods for adding, removing, and accessing tasks in the list.
 *
 * @see Task
 * @see Iterable
 */
public class TaskList implements Iterable<Task> {
    protected ArrayList<Task> tasks;

    /**
     * Constructs a TaskList with the specified initial list of tasks.
     *
     * @param tasks the initial list of tasks to populate the TaskList with,
     *              can be null or empty
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Constructs an empty TaskList with no initial tasks.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the number of tasks in this TaskList
     */
    public int size() {
        return this.tasks.size();
    }

    /**
     * Returns the task at the specified position in the list.
     *
     * @param index the index of the task to return (0-based)
     * @return the task at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (index < 0 || index >= size())
     */
    public Task get(int index) {
        return this.tasks.get(index);
    }

    /**
     * Appends the specified task to the end of the list.
     *
     * @param task the task to be added to the list, cannot be null
     * @throws NullPointerException if the specified task is null
     */
    public void add(Task task) {
        this.tasks.add(task);
    }

    /**
     * Removes the task at the specified position in the list.
     *
     * @param index the index of the task to be removed (0-based)
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (index < 0 || index >= size())
     */
    public void remove(int index) {
        this.tasks.remove(index);
    }

    /**
     * Returns true if this list contains no tasks.
     *
     * @return true if this list contains no tasks
     */
    public boolean isEmpty() {
        return this.tasks.isEmpty();
    }

    /**
     * Returns the underlying ArrayList of tasks.
     *
     * @return the ArrayList containing all tasks in this TaskList
     */
    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    /**
     * Returns a formatted string representation of all tasks in the list.
     * Each task is numbered and indented, with tasks separated by newlines.
     * The format is: "  [index].[task toString representation]"
     *
     * @return a formatted string listing all tasks with their indices
     */
    @Override
    public String toString() {
        StringBuilder taskString = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            taskString.append("  ").append(i + 1).append(".").append(tasks.get(i));
            if (i < tasks.size() - 1) {
                taskString.append("\n");
            }
        }
        return taskString.toString();
    }

    /**
     * Returns an iterator over the tasks in this list in proper sequence.
     *
     * @return an iterator over the tasks in this list
     */
    @Override
    public Iterator<Task> iterator() {
        return tasks.iterator();
    }
}

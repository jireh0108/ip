package nova.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskListTest {
    private TaskList taskList;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
    }

    @Test
    void testIsEmptyOnNewList() {
        assertTrue(taskList.isEmpty(), "New TaskList should be empty");
        assertEquals(0, taskList.size(), "Size of new TaskList should be 0");
    }

    @Test
    void testAddTaskIncreasesSize() {
        Task task = new Task("buy milk");
        taskList.add(task);
        assertEquals(1, taskList.size(), "Size should increase after adding a task");
        assertFalse(taskList.isEmpty(), "TaskList should not be empty after adding a task");
        assertEquals(task, taskList.get(0), "The added task should be retrievable");
    }

    @Test
    void testRemoveTaskDecreasesSize() {
        Task task1 = new Task("task 1");
        Task task2 = new Task("task 2");
        taskList.add(task1);
        taskList.add(task2);

        taskList.remove(0);

        assertEquals(1, taskList.size(), "Size should decrease after removal");
        assertEquals(task2, taskList.get(0), "Remaining task should shift into index 0");
    }

    @Test
    void testGetTasksReturnsUnderlyingList() {
        Task task = new Task("do homework");
        taskList.add(task);

        ArrayList<Task> tasks = taskList.getTasks();
        assertEquals(1, tasks.size());
        assertTrue(tasks.contains(task));
    }

    @Test
    void testToStringWithMultipleTasks() {
        Task task1 = new Task("clean room");
        Task task2 = new Task("buy bread");
        taskList.add(task1);
        taskList.add(task2);

        String expected =
                "  1." + task1.toString() + "\n"
                        + "  2." + task2.toString();

        assertEquals(expected, taskList.toString(), "toString() should list tasks with numbering and line breaks");
    }

    @Test
    void testIteratorIteratesOverTasks() {
        Task task1 = new Task("task 1");
        Task task2 = new Task("task 2");
        taskList.add(task1);
        taskList.add(task2);

        int count = 0;
        for (Task t : taskList) {
            assertNotNull(t);
            count++;
        }

        assertEquals(2, count, "Iterator should loop over all tasks");
    }
}

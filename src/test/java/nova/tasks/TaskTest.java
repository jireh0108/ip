package nova.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task("buy milk");
    }

    @Test
    void testNewTaskIsNotDone() {
        assertFalse(task.getStatus(), "New task should not be marked done");
        assertEquals(" ", task.getStatusIcon(), "Status icon for not done should be a space");
    }

    @Test
    void testGetDescription() {
        assertEquals("buy milk", task.getDescription(), "Description should match constructor input");
    }

    @Test
    void testMarkSetsTaskDone() {
        task.mark();
        assertTrue(task.getStatus(), "Task should be marked done after mark()");
        assertEquals("X", task.getStatusIcon(), "Status icon should be X when done");
    }

    @Test
    void testUnmarkResetsTaskToNotDone() {
        task.mark();
        task.unmark();
        assertFalse(task.getStatus(), "Task should not be done after unmark()");
        assertEquals(" ", task.getStatusIcon(), "Status icon should reset to space after unmark()");
    }

    @Test
    void testToStringWhenNotDone() {
        assertEquals("[ ] buy milk", task.toString(),
                "toString() should show unchecked box and description when not done");
    }

    @Test
    void testToStringWhenDone() {
        task.mark();
        assertEquals("[X] buy milk", task.toString(),
                "toString() should show X box and description when done");
    }
}

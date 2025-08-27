package nova.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ToDoTest {
    @Test
    public void ToDoFormatTest() {
        ToDo task = new ToDo("buy bread");
        assertEquals(task.toString(), "[T][ ] buy bread");
    }
}

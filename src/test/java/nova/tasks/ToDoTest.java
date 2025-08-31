package nova.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ToDoTest {
    @Test
    public void toDoFormatTest() {
        ToDo task = new ToDo("buy bread");
        assertEquals("[T][ ] buy bread", task.toString());
    }
}

package nova.tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDoTest {
    @Test
    public void ToDoFormatTest() {
        ToDo task = new ToDo("buy bread");
        assertEquals(task.toString(), "[T][ ] buy bread");
    }
}

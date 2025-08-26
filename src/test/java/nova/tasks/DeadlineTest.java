package nova.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DeadlineTest {
    private Deadline deadline;
    private LocalDateTime by;

    @BeforeEach
    void setUp() {
        by = LocalDateTime.of(2025, 9, 1, 23, 59); // Sep 1, 2025, 23:59
        deadline = new Deadline("Submit assignment", by);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("Submit assignment", deadline.getDescription(),
                "Description should match constructor input");
        assertEquals(by, deadline.getBy(),
                "Deadline datetime should match constructor input");
        assertFalse(deadline.getStatus(),
                "New Deadline should not be marked done by default");
    }

    @Test
    void testMarkAndUnmark() {
        deadline.mark();
        assertTrue(deadline.getStatus(),
                "Deadline should be marked done after mark()");
        deadline.unmark();
        assertFalse(deadline.getStatus(),
                "Deadline should not be done after unmark()");
    }

    @Test
    void testToStringWhenNotDone() {
        String expected = "[D][ ] Submit assignment (by: Sep 1 2025 2359)";
        assertEquals(expected, deadline.toString(),
                "toString() should format deadline correctly when not done");
    }

    @Test
    void testToStringWhenDone() {
        deadline.mark();
        String expected = "[D][X] Submit assignment (by: Sep 1 2025 2359)";
        assertEquals(expected, deadline.toString(),
                "toString() should format deadline correctly when done");
    }
}

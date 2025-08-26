package nova.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {
    private Event event;
    private LocalDateTime from;
    private LocalDateTime to;

    @BeforeEach
    void setUp() {
        from = LocalDateTime.of(2025, 8, 27, 14, 0); // Aug 27 2025, 14:00
        to = LocalDateTime.of(2025, 8, 27, 16, 30);  // Aug 27 2025, 16:30
        event = new Event("project meeting", from, to);
    }

    @Test
    void testConstructorSetsFieldsCorrectly() {
        assertEquals("project meeting", event.getDescription(), "Description should match input");
        assertEquals(from, event.getFrom(), "Start time should match input");
        assertEquals(to, event.getTo(), "End time should match input");
        assertFalse(event.getStatus(), "New event should not be marked done by default");
    }

    @Test
    void testMarkAndUnmark() {
        event.mark();
        assertTrue(event.getStatus(), "Event should be marked done after mark()");
        event.unmark();
        assertFalse(event.getStatus(), "Event should be not done after unmark()");
    }

    @Test
    void testToStringWhenNotDone() {
        String expected = "[E][ ] project meeting (from: Aug 27 2025 1400 to: Aug 27 2025 1630)";
        assertEquals(expected, event.toString(),
                "toString() should format event correctly with status and date range");
    }

    @Test
    void testToStringWhenDone() {
        event.mark();
        String expected = "[E][X] project meeting (from: Aug 27 2025 1400 to: Aug 27 2025 1630)";
        assertEquals(expected, event.toString(),
                "toString() should show done status with X when marked");
    }
}

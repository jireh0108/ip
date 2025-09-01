package nova.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UiTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private Ui ui;

    @BeforeEach
    void setUp() {
        ui = new Ui();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testShowWelcome() {
        String output = ui.showWelcome();
        assertTrue(output.contains("Hello! I'm Nova :3"), "Welcome message should be displayed");
        assertTrue(output.contains("Enter \"help\" to see available commands!"),
                "Help instructions should be displayed");
    }

    @Test
    void testShowLoadingError() {
        String output = ui.showLoadingError();
        assertTrue(output.contains("Loading failed..."), "Loading error message should be displayed");
    }

    @Test
    void testShowError() {
        String output = ui.showError("Something went wrong");
        assertTrue(output.contains("Error: Something went wrong"), "Error message should include prefix");
    }

    @Test
    void testReadCommand() {
        String simulatedInput = "test command\n";
        ui = new Ui();
        ui.scanner = new java.util.Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));
        String input = ui.readCommand();
        assertEquals("test command", input, "readCommand() should return user input");
    }
}

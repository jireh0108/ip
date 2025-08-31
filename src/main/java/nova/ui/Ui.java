package nova.ui;

import java.util.Scanner;
/**
 * Handles all user interactions in the Nova application.
 * <p>
 * This class is responsible for displaying messages, prompts, and errors to the user,
 * as well as reading user input from the console. It also provides a visual divider line
 * to separate Nova's responses for clarity.
 * </p>
 */
public class Ui {
    /**
     * Dividing line to separate Nova's responses
     */
    protected Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Prints out welcome message
     */
    public String showWelcome() {
        return """
                Hello! I'm Nova :3
                What can I do for you?
                Enter "help" to see available commands!
                """;
    }

    /**
     * Displays loading error message
     */
    public String showLoadingError() {
        return "Loading failed...";
    }

    /**
     * Prompts user for input
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays error message
     */
    public String showError(String msg) {
        return "Error: " + msg;
    }
}

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
    protected final String dividingLine = "____________________________________________________________";
    protected Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Prints out dividing line
     */
    public void showLine() {
        System.out.println(dividingLine);
    }

    /**
     * Prints out welcome message
     */
    public void showWelcome() {
        System.out.println(dividingLine
                + "\nHello! I'm Nova :3\n"
                + "What can I do for you?\n"
                + "Enter \"help\" to see available commands!\n"
                + dividingLine);
    }

    /**
     * Displays loading error message
     */
    public void showLoadingError() {
        System.out.println(dividingLine + "Loading failed...");
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
    public void showError(String msg) {
        System.out.println("Error: " + msg);
    }

    /**
     * Displays given message
     */
    public void showText(String msg) {
        System.out.println(msg);
    }
}

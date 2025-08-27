package nova.ui;

import java.util.Scanner;

public class Ui {
    /** Dividing line to separate Nova's responses */
    protected String LINE = "____________________________________________________________";
    Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }
    /** Prints out dividing line */
    public void showLine() {
        System.out.println(LINE);
    }
    /** Prints out welcome message */
    public void showWelcome() {
        System.out.println(LINE +
                "\nHello! I'm Nova :3\n" +
                "What can I do for you?\n" +
                "Enter \"help\" to see available commands!\n" +
                LINE);
    }
    /** Displays loading error message */
    public void showLoadingError() {
        System.out.println(LINE + "Loading failed...");
    }
    /** Prompts user for input */
    public String readCommand() {
        return scanner.nextLine();
    }
    /** Displays error message */
    public void showError(String msg) {
        System.out.println("Error: " + msg);
    }
    /** Displays given message */
    public void showText(String msg) {
        System.out.println(msg);
    }
}

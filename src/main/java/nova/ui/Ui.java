package nova.ui;

import java.util.Scanner;

public class Ui {
    protected String DIVIDER = "____________________________________________________________\n";
    Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showLine() {
        System.out.println(DIVIDER);
    }

    public void showWelcome() {
        System.out.println(DIVIDER +
                "Hello! I'm nova :3\n" +
                "What can I do for you?\n" +
                "Enter \"help\" to see available commands!\n" +
                DIVIDER);
    }

    public void showLoadingError() {
        System.out.println(DIVIDER + "Loading failed...\n" + DIVIDER);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showError(String msg) {
        System.out.println("Error: " + msg);
    }

    public void showText(String msg) {
        System.out.println(msg);
    }
}

package main.java;
import java.util.Scanner;

public class Nova {
    private static final String DIVIDER = "____________________________________________________________\n";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(DIVIDER +
                "Hello! I'm Nova :3\n" + "What can I do for you?\n" +
                DIVIDER);

        String line = scanner.nextLine();
        while (!line.equals("bye")) {
            System.out.println(DIVIDER + line + "\n" + DIVIDER);
            line = scanner.nextLine();
        }
        System.out.println(DIVIDER + "Bye. Hope to see you again soon!\n" + DIVIDER);
        return;
    }
}
package main.java;
import java.util.Scanner;

public class Nova {
    public static void main(String[] args) {
        String divider = "____________________________________________________________\n";
        Scanner scanner = new Scanner(System.in);
        System.out.println(divider +
                "Hello! I'm Nova :3\n" + "What can I do for you?\n" +
                divider);

        String line = scanner.nextLine();
        while (!line.equals("bye")) {
            System.out.println(divider + line + "\n" + divider);
            line = scanner.nextLine();
        }
        System.out.println(divider + "Bye. Hope to see you again soon!\n" + divider);
        return;
    }
}
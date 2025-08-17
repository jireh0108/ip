package main.java;
import java.util.Scanner;

public class Nova {
    private static final String DIVIDER = "____________________________________________________________\n";
    private static final String[] list = new String[100];
    private static int listSize = 0;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println(DIVIDER +
                "Hello! I'm Nova :3\n" + "What can I do for you?\n" +
                DIVIDER);

        String line = scanner.nextLine();
        while (!line.equals("bye")) {
            if (line.equals("list")) {
                StringBuilder tasks = new StringBuilder();
                for (int i = 1; i < listSize + 1; i++) {
                    tasks.append(i).append(". ").append(list[i - 1]).append("\n");
                }
                System.out.println(DIVIDER + tasks + DIVIDER);
                line = scanner.nextLine();
            }
            System.out.println(DIVIDER + "added: " + line + "\n" + DIVIDER);
            list[listSize] = line;
            listSize++;
            line = scanner.nextLine();
        }

        System.out.println(DIVIDER + "Bye. Hope to see you again soon!\n" + DIVIDER);
        return;
    }
}
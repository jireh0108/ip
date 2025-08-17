package main.java;
import java.util.Scanner;

public class Nova {
    private static final String DIVIDER = "____________________________________________________________\n";
    private static final Task[] list = new Task[100];
    private static int listSize = 0;

    private enum Command {
        list, mark, unmark, bye
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(DIVIDER +
                "Hello! I'm Nova :3\n" +
                "What can I do for you?\n" +
                DIVIDER);
        String line = scanner.nextLine();

        while (!line.equals("bye")) {
            String[] parts = line.split(" ");
            String commandStr = parts[0];

            try {
                //convert input to command
                Command command = Command.valueOf(commandStr);

                switch (command) {
                    case list -> {
                        StringBuilder taskString = new StringBuilder();
                        for (int i = 0; i < listSize; i++) {
                            taskString.append(i + 1).append(".").append(list[i]).append("\n");
                        }
                        System.out.println(DIVIDER + taskString + DIVIDER);
                    }

                    case mark -> {
                        if (parts.length == 2 && parts[1].matches("\\d+")) {
                            int index = Integer.parseInt(parts[1]) - 1;
                            if (index >= 0 && index < listSize) {
                                Task curr = list[index];
                                if (!curr.getStatus()) {
                                    curr.mark();
                                    System.out.println(DIVIDER + "Nice! I've marked this task as done:\n" +
                                            curr + "\n" + DIVIDER);
                                } else {
                                    System.out.println(DIVIDER + "The task is already marked!" + "\n" + DIVIDER);
                                }
                            } else {
                                System.out.println(DIVIDER + "Invalid task number!\n" + DIVIDER);
                            }
                        } else {
                            System.out.println(DIVIDER + "Usage: mark <task number>\n" + DIVIDER);
                        }
                    }

                    case unmark -> {
                        if (parts.length == 2 && parts[1].matches("\\d+")) {
                            int index = Integer.parseInt(parts[1]) - 1;
                            if (index >= 0 && index < listSize) {
                                Task curr = list[index];
                                if (curr.getStatus()) {
                                    curr.unmark();
                                    System.out.println(DIVIDER + "OK, I've marked this task as not done yet:\n" +
                                            curr + "\n" + DIVIDER);
                                } else {
                                    System.out.println(DIVIDER + "The task is already unmarked!" + DIVIDER);
                                }
                            } else {
                                System.out.println(DIVIDER + "Invalid task number!\n" + DIVIDER);
                            }
                        } else {
                            System.out.println(DIVIDER + "Usage: unmark <task number>\n" + DIVIDER);
                        }
                    }
                }

            } catch (IllegalArgumentException e) {
                if (!line.isBlank()) {
                    list[listSize] = new Task(line);
                    listSize++;
                    System.out.println(DIVIDER + "Added: " + line + "\n" + DIVIDER);
                }
            }
            line = scanner.nextLine();
        }

        System.out.println(DIVIDER + "Bye. Hope to see you again soon!\n" + DIVIDER);
    }
}

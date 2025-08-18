package main.java;
import java.util.Scanner;

public class Nova {
    private static final String DIVIDER = "____________________________________________________________\n";
    private static final Task[] list = new Task[100];
    private static int listSize = 0;

    private enum Command {
        list, mark, unmark, bye, todo, deadline, event
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(DIVIDER +
                "Hello! I'm Nova :3\n" +
                "What can I do for you?\n" +
                DIVIDER);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equals("bye")) {
                break;
            }
            String[] parts = line.split(" ", 2);
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
                        System.out.println(DIVIDER + "Here are the tasks in your list:\n" + taskString + DIVIDER);
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
                                    System.out.println(DIVIDER + "The task is already unmarked!\n" + DIVIDER);
                                }
                            } else {
                                System.out.println(DIVIDER + "Invalid task number!\n" + DIVIDER);
                            }
                        } else {
                            System.out.println(DIVIDER + "Usage: unmark <task number>\n" + DIVIDER);
                        }
                    }

                    case todo -> {
                        String description = parts[1];
                        Task curr = new ToDo(description);
                        list[listSize] = curr;
                        listSize++;
                        System.out.println(DIVIDER + "Got it. I've added this task:\n" +
                                "  " + curr + "\n" +
                                "Now you have " + listSize + " tasks in the list.\n" + DIVIDER);
                    }

                    case deadline -> {
                        String[] remainder = parts[1].split(" /by ", 2);
                        String description = remainder[0];
                        String deadline = remainder[1];
                        Task curr = new Deadline(description, deadline);
                        list[listSize] = curr;
                        listSize++;
                        System.out.println(DIVIDER + "Got it. I've added this task:\n" +
                                "  " + curr + "\n" +
                                "Now you have " + listSize + " tasks in the list.\n" + DIVIDER);
                    }

                    case event -> {
                        String[] remainder = parts[1].split(" /from ", 2);
                        String description = remainder[0];
                        String[] timings = remainder[1].split(" /to ", 2);
                        String from = timings[0];
                        String to = timings[1];
                        Task curr = new Event(description, from, to);
                        list[listSize] = curr;
                        listSize++;
                        System.out.println(DIVIDER + "Got it. I've added this task:\n" +
                                "  " + curr + "\n" +
                                "Now you have " + listSize + " tasks in the list.\n" + DIVIDER);
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        }

        System.out.println(DIVIDER + "Bye. Hope to see you again soon!\n" + DIVIDER);
    }
}

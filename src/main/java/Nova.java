package main.java;
import java.util.Scanner;
import java.util.ArrayList;

public class Nova {
    private static final String DIVIDER = "____________________________________________________________\n";
    private static final ArrayList<Task> listOfTasks = new ArrayList<>();

    private enum Command {
        list, mark, unmark, bye, todo, deadline, event, help
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(DIVIDER +
                "Hello! I'm Nova :3\n" +
                "What can I do for you?\n" +
                "Enter \"help\" to see available commands!\n" +
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
                        for (int i = 0; i < listOfTasks.size(); i++) {
                            taskString.append(i + 1).append(".").append(listOfTasks.get(i)).append("\n");
                        }
                        System.out.println(DIVIDER + "Here are the tasks in your list:\n" + taskString + DIVIDER);
                    }

                    case mark -> {
                        if (parts.length == 2 && parts[1].matches("\\d+")) {
                            int index = Integer.parseInt(parts[1]) - 1;
                            if (index >= 0 && index < listOfTasks.size()) {
                                Task curr = listOfTasks.get(index);
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
                            if (index >= 0 && index < listOfTasks.size()) {
                                Task curr = listOfTasks.get(index);
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
                        if (parts.length < 2 || parts[1].isBlank()) {
                            System.out.println(DIVIDER + "Usage: todo <description>\n" + DIVIDER);
                            break;
                        }
                        String description = parts[1];
                        Task curr = new ToDo(description);
                        listOfTasks.add(curr);
                        System.out.println(DIVIDER + "Got it. I've added this task:\n" +
                                "  " + curr + "\n" +
                                "Now you have " + listOfTasks.size() + " tasks in the list.\n" + DIVIDER);
                    }

                    case deadline -> {
                        if (parts.length < 2 || !parts[1].contains(" /by ")) {
                            System.out.println(DIVIDER + "Usage: deadline <description> /by <deadline>\n" + DIVIDER);
                            break;
                        }
                        String[] remainder = parts[1].split(" /by ", 2);
                        String description = remainder[0];
                        String deadline = remainder[1];
                        Task curr = new Deadline(description, deadline);
                        listOfTasks.add(curr);
                        System.out.println(DIVIDER + "Got it. I've added this task:\n" +
                                "  " + curr + "\n" +
                                "Now you have " + listOfTasks.size() + " tasks in the list.\n" + DIVIDER);
                    }

                    case event -> {
                        if (parts.length < 2 || !parts[1].contains(" /from ") || !parts[1].contains(" /to ")) {
                            System.out.println(DIVIDER + "Usage: event <description> /from <start> /to <end>\n" + DIVIDER);
                            break;
                        }
                        String[] remainder = parts[1].split(" /from ", 2);
                        String description = remainder[0];
                        String[] timings = remainder[1].split(" /to ", 2);
                        String from = timings[0];
                        String to = timings[1];
                        Task curr = new Event(description, from, to);
                        listOfTasks.add(curr);
                        System.out.println(DIVIDER + "Got it. I've added this task:\n" +
                                "  " + curr + "\n" +
                                "Now you have " + listOfTasks.size() + " tasks in the list.\n" + DIVIDER);
                    }

                    case help -> {
                        System.out.println(DIVIDER +
                                "Here are the available commands:\n" +
                                "  todo <description>\n" +
                                "    → Adds a todo task. Example: todo borrow book\n\n" +
                                "  deadline <description> /by <time>\n" +
                                "    → Adds a task with a deadline. Example: deadline return book /by Sunday\n\n" +
                                "  event <description> /from <start> /to <end>\n" +
                                "    → Adds an event with a time range. Example: event project meeting /from Mon 2pm /to 4pm\n\n" +
                                "  list\n" +
                                "    → Shows all tasks in your list.\n\n" +
                                "  mark <task number>\n" +
                                "    → Marks a task as done.\n\n" +
                                "  unmark <task number>\n" +
                                "    → Marks a task as not done.\n\n" +
                                "  bye\n" +
                                "    → Exits the program.\n" +
                                DIVIDER);
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        }

        System.out.println(DIVIDER + "Bye. Hope to see you again soon!\n" + DIVIDER);
    }
}

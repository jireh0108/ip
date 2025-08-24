import tasks.*;
import storage.Storage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Nova {
    private static final String DIVIDER = "____________________________________________________________\n";
    private TaskList tasks;
    private final Storage storage;

    private enum Command {
        list, mark, unmark, bye, todo, deadline, event, help, delete, schedule
    }

    public Nova(String filePath) {
        this.storage = new Storage(filePath);
        TaskList loaded = storage.load();
        this.tasks = (loaded != null) ? loaded : new TaskList();
    }
    public static void main(String[] args) {
        new Nova("data/tasks.txt").run();
    }

    public void run() {
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
                Command command = Command.valueOf(commandStr);

                switch (command) {
                case list:
                    handleList();
                    break;
                case mark:
                    handleMark(parts);
                    this.storage.write(tasks);
                    break;
                case unmark:
                    handleUnmark(parts);
                    this.storage.write(tasks);
                    break;
                case todo:
                    handleTodo(parts);
                    this.storage.write(tasks);
                    break;
                case deadline:
                    handleDeadline(parts);
                    this.storage.write(tasks);
                    break;
                case event:
                    handleEvent(parts);
                    this.storage.write(tasks);
                    break;
                case help:
                    handleHelp();
                    break;
                case delete:
                    handleDelete(parts);
                    this.storage.write(tasks);
                    break;
                case schedule:
                    handleSchedule(parts);
                    break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        }

        System.out.println(DIVIDER + "Bye. Hope to see you again soon!\n" + DIVIDER);
    }

    private void handleList() {
        if (tasks.isEmpty()) {
            System.out.println(DIVIDER + "There are no tasks! Try \"help\" for commands!\n" + DIVIDER);
            return;
        }
        StringBuilder taskString = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            taskString.append("  ").append(i + 1).append(".").append(tasks.get(i)).append("\n");
        }
        System.out.println(DIVIDER + "Here are the tasks in your list:\n" + taskString + DIVIDER);
    }

    private void handleMark(String[] parts) {
        if (parts.length == 2 && parts[1].matches("\\d+")) {
            int index = Integer.parseInt(parts[1]) - 1;
            if (index >= 0 && index < tasks.size()) {
                Task curr = tasks.get(index);
                if (!curr.getStatus()) {
                    curr.mark();
                    System.out.println(DIVIDER + "Nice! I've marked this task as done:\n  " + curr + "\n" + DIVIDER);
                } else {
                    System.out.println(DIVIDER + "The task is already marked!\n" + DIVIDER);
                }
            } else {
                System.out.println(DIVIDER + "Invalid task number!\n" + DIVIDER);
            }
        } else {
            System.out.println(DIVIDER + "Usage: mark <task number>\n" + DIVIDER);
        }
    }

    private void handleUnmark(String[] parts) {
        if (parts.length == 2 && parts[1].matches("\\d+")) {
            int index = Integer.parseInt(parts[1]) - 1;
            if (index >= 0 && index < tasks.size()) {
                Task curr = tasks.get(index);
                if (curr.getStatus()) {
                    curr.unmark();
                    System.out.println(DIVIDER + "OK, I've marked this task as not done yet:\n  " + curr + "\n" + DIVIDER);
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

    private void handleTodo(String[] parts) {
        if (parts.length < 2 || parts[1].isBlank()) {
            System.out.println(DIVIDER + "Usage: todo <description>\n" + DIVIDER);
            return;
        }
        String description = parts[1];
        Task curr = new ToDo(description);
        tasks.add(curr);
        System.out.println(DIVIDER + "Got it. I've added this task:\n  " + curr +
                "\nNow you have " + tasks.size() + " tasks in the list.\n" + DIVIDER);
    }

    private void handleDeadline(String[] parts) {
        if (parts.length < 2 || !parts[1].contains(" /by ")) {
            System.out.println(DIVIDER + "Usage: deadline <description> /by <deadline>\n" + DIVIDER);
            return;
        }
        String[] remainder = parts[1].split(" /by ", 2);
        String description = remainder[0];
        LocalDateTime deadline = parseDateTime(remainder[1]);
        if (deadline == null) return;
        Task curr = new Deadline(description, deadline);
        tasks.add(curr);
        System.out.println(DIVIDER + "Got it. I've added this task:\n  " + curr +
                "\nNow you have " + tasks.size() + " tasks in the list.\n" + DIVIDER);
    }

    private void handleEvent(String[] parts) {
        if (parts.length < 2 || !parts[1].contains(" /from ") || !parts[1].contains(" /to ")) {
            System.out.println(DIVIDER + "Usage: event <description> /from <start> /to <end>\n" + DIVIDER);
            return;
        }
        String[] remainder = parts[1].split(" /from ", 2);
        String description = remainder[0];
        String[] timings = remainder[1].split(" /to ", 2);
        LocalDateTime from = parseDateTime(timings[0]);
        LocalDateTime to = parseDateTime(timings[1]);
        if (from == null || to == null) return;
        Task curr = new Event(description, from, to);
        tasks.add(curr);
        System.out.println(DIVIDER + "Got it. I've added this task:\n  " + curr +
                "\nNow you have " + tasks.size() + " tasks in the list.\n" + DIVIDER);
    }

    private void handleHelp() {
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
                "  schedule <date>\n" +
                "    → Shows all tasks on that date.\n\n" +
                "  mark <task number>\n" +
                "    → Marks a task as done.\n\n" +
                "  unmark <task number>\n" +
                "    → Marks a task as not done.\n\n" +
                "  bye\n" +
                "    → Exits the program.\n" +
                DIVIDER);
    }

    private void handleDelete(String[] parts) {
        if (parts.length == 2 && parts[1].matches("\\d+")) {
            int index = Integer.parseInt(parts[1]) - 1;
            if (index >= 0 && index < tasks.size()) {
                Task curr = tasks.get(index);
                tasks.remove(index);
                System.out.println(DIVIDER + "Noted. I've removed this task:\n  " + curr +
                        "\nNow you have " + tasks.size() + " tasks in the list.\n" + DIVIDER);
            } else {
                System.out.println(DIVIDER + "Invalid task number!\n" + DIVIDER);
            }
        } else {
            System.out.println(DIVIDER + "Usage: delete <task number>\n" + DIVIDER);
        }
    }

    private void handleSchedule(String[] parts) {
        if (parts.length < 2 || parts[1].isBlank()) {
            System.out.println(DIVIDER + "Usage: schedule <date>\nExample: schedule 2025-08-25\n" + DIVIDER);
            return;
        }

        String dateStr = parts[1].trim();
        LocalDateTime parsedDateTime = parseDateTime(dateStr);
        if (parsedDateTime == null) {
            return;
        }

        LocalDate queryDate = parsedDateTime.toLocalDate();
        StringBuilder result = new StringBuilder();
        for (Task task : tasks) {
            if (task instanceof Deadline d) {
                if (d.getBy().toLocalDate().equals(queryDate)) {
                    result.append(d).append("\n");
                }
            } else if (task instanceof Event e) {
                LocalDate fromDate = e.getFrom().toLocalDate();
                LocalDate toDate = e.getTo().toLocalDate();
                if (!queryDate.isBefore(fromDate) && !queryDate.isAfter(toDate)) {
                    result.append(e).append("\n");
                }
            }
        }

        if (result.isEmpty()) {
            System.out.println(DIVIDER + "No deadlines or events found on " + queryDate + "\n" + DIVIDER);
        } else {
            System.out.println(DIVIDER + "Scheduled tasks on " + queryDate + ":\n" + result + DIVIDER);
        }
    }

    private static LocalDateTime parseDateTime(String dateStr) {
        // try date and time
        try {
            return LocalDateTime.parse(dateStr.trim(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException ignored) {}

        // try date only
        try {
            LocalDate date = LocalDate.parse(dateStr.trim(), DateTimeFormatter.ISO_LOCAL_DATE);
            return date.atStartOfDay();
        } catch (DateTimeParseException ignored) {}

        DateTimeFormatter[] customFormats = {
                DateTimeFormatter.ofPattern("d/M/yyyy HHmm"),
                DateTimeFormatter.ofPattern("d/M/yyyy"),
                DateTimeFormatter.ofPattern("d MMM yyyy HHmm"),
                DateTimeFormatter.ofPattern("d MMM yyyy"),
                DateTimeFormatter.ofPattern("MMM d yyyy HHmm"),
                DateTimeFormatter.ofPattern("MMM d yyyy"),
        };
        for (DateTimeFormatter fmt : customFormats) {
            try {
                return LocalDateTime.parse(dateStr.trim(), fmt);
            } catch (DateTimeParseException ignored) {
                try {
                    LocalDate date = LocalDate.parse(dateStr.trim(), fmt);
                    return date.atStartOfDay();
                } catch (DateTimeParseException ignored2) {}
            }
        }
        System.out.println(DIVIDER + "\nInvalid date format: " + dateStr
                + "\nTry something like Dec 31 2025 or 31/12/2025!\n" + DIVIDER);
        return null;
    }
}

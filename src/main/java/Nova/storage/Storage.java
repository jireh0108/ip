package Nova.storage;

import Nova.tasks.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final File tasksFile;
    private TaskList tasks;
    private static final String DIVIDER = "____________________________________________________________\n";

    public Storage(String filePath) {
        this.tasksFile = new File(filePath);
        try {
            if (tasksFile.getParentFile() != null) {
                tasksFile.getParentFile().mkdirs();
            }
            tasksFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Failed to create data file: " + tasksFile.getAbsolutePath(), e);
        }
    }

    public TaskList load() {
        TaskList loadedTasks = new TaskList();
        try (Scanner fileScanner = new Scanner(tasksFile)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(" \\| ");
                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                Task task = null;

                switch (type) {
                    case "T":
                        task = new ToDo(parts[2]);
                        break;
                    case "D":
                        LocalDateTime deadline = parseDateTime(parts[3]);
                        if (deadline != null) task = new Deadline(parts[2], deadline);
                        break;
                    case "E":
                        LocalDateTime from = parseDateTime(parts[3]);
                        LocalDateTime to = parseDateTime(parts[4]);
                        if (from != null && to != null) task = new Event(parts[2], from, to);
                        break;
                }

                if (task != null) {
                    if (isDone) task.mark();
                    loadedTasks.add(task);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading tasks from file: " + tasksFile.getAbsolutePath());
        }

        return loadedTasks;
    }

    public void write(TaskList tasks) {
        try (FileWriter writer = new FileWriter(tasksFile, false)) { // overwrite
            for (Task task : tasks) {
                String line = "";
                if (task instanceof ToDo) {
                    line = "T | " + (task.getStatus() ? "1" : "0") + " | " + task.getDescription();
                } else if (task instanceof Deadline d) {
                    line = "D | " + (task.getStatus() ? "1" : "0") + " | " + d.getDescription() + " | " + d.getBy();
                } else if (task instanceof Event e) {
                    line = "E | " + (task.getStatus() ? "1" : "0") + " | " + e.getDescription() + " | " + e.getFrom() + " | " + e.getTo();
                }
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing tasks to file: " + tasksFile.getAbsolutePath());
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

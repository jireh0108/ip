package nova.storage;

import static nova.parser.Parser.parseDateTime;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;

import nova.tasks.Deadline;
import nova.tasks.Event;
import nova.tasks.Task;
import nova.tasks.TaskList;
import nova.tasks.ToDo;

public class Storage {
    /**
     * File to be read and written to
     */
    private final File tasksFile;
    ;

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

    /**
     * Reads Strings from a .txt file as Tasks and returns a TaskList object.
     *
     * @return TaskList object.
     */
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

    /**
     * Reads Tasks from a TaskList object and writes them as Strings to the Storage object's taskFile.
     *
     * @param tasks TaskList object.
     */
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
}

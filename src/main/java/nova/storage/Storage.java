package nova.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import nova.parser.Parser;
import nova.tasks.Deadline;
import nova.tasks.Event;
import nova.tasks.Task;
import nova.tasks.TaskList;
import nova.tasks.ToDo;

/**
 * Represents storage for Nova's tasks. Handles reading from and writing to a file
 * to persist TaskList objects between sessions.
 */
public class Storage {
    /** File used to store tasks */
    private final File tasksFile;

    /**
     * Constructs a Storage object that manages the given file path.
     *
     * @param filePath Path to the file used for storing tasks.
     */
    public Storage(String filePath) {
        this.tasksFile = new File(filePath);
        try {
            if (tasksFile.getParentFile() != null) {
                tasksFile.getParentFile().mkdirs();
            }
            tasksFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to create data file: " + tasksFile.getAbsolutePath(), e);
        }

        assert tasksFile.exists() : "Tasks file must exist after constructor";
    }

    /**
     * Loads tasks from the storage file and converts them into a TaskList.
     *
     * @return TaskList containing tasks loaded from the file.
     */
    public TaskList load() {
        TaskList loadedTasks = new TaskList();

        try (Scanner fileScanner = new Scanner(tasksFile)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                Task task = Parser.parseStorageTaskString(line);
                if (task != null) {
                    loadedTasks.add(task);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading tasks from file: " + tasksFile.getAbsolutePath());
        }

        return loadedTasks;
    }

    /**
     * Writes the given TaskList to the storage file.
     *
     * @param tasks TaskList to write to the file.
     */
    public void write(TaskList tasks) {
        try (FileWriter writer = new FileWriter(tasksFile, false)) { // overwrite
            for (Task task : tasks) {
                String line;
                if (task instanceof ToDo) {
                    line = "T | " + (task.getStatus() ? "1" : "0")
                            + " | " + task.getDescription();
                } else if (task instanceof Deadline d) {
                    line = "D | " + (task.getStatus() ? "1" : "0")
                            + " | " + d.getDescription()
                            + " | " + d.getBy();
                } else if (task instanceof Event e) {
                    line = "E | " + (task.getStatus() ? "1" : "0")
                            + " | " + e.getDescription()
                            + " | " + e.getFrom()
                            + " | " + e.getTo();
                } else {
                    line = ""; // fallback for unknown task types
                }
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            System.err.println(
                    "Error writing tasks to file: " + tasksFile.getAbsolutePath());
        }
    }
}

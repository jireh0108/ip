package nova;

import nova.commands.Command;
import nova.exceptions.NovaException;
import nova.parser.Parser;
import nova.storage.Storage;
import nova.tasks.TaskList;
import nova.ui.Ui;

/**
 * Nova is the main class that runs the task management application.
 * It handles the initialization of the Storage, TaskList, and Ui,
 * and runs the main command loop.
 */
public class Nova {
    private final Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs a new Nova instance with the given file path for storage.
     * Loads tasks from the file if it exists, otherwise starts with an empty TaskList.
     *
     * @param filePath Path to the file used to save and load tasks.
     */
    public Nova(String filePath) {
        ui = new Ui();
        this.storage = new Storage(filePath);
        try {
            TaskList loaded = storage.load();
            this.tasks = (loaded != null) ? loaded : new TaskList();
        } catch (NovaException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Entry point for the Nova application.
     *
     * @param args Command-line arguments (ignored).
     */
    public static void main(String[] args) {
        new Nova("data/tasks.txt").run();
    }

    /**
     * Starts the main loop of the application.
     * Continuously reads commands from the user, executes them, and
     * handles exceptions until an ExitCommand is issued.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (NovaException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }
}


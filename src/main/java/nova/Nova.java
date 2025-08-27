package nova;

import nova.commands.Command;
import nova.exceptions.NovaException;
import nova.parser.Parser;
import nova.storage.Storage;
import nova.tasks.TaskList;
import nova.ui.Ui;

public class Nova {
    private final Storage storage;
    private TaskList tasks;
    private Ui ui;

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

    public static void main(String[] args) {
        new Nova("data/tasks.txt").run();
    }

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

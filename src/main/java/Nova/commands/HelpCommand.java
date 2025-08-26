package Nova.commands;
import Nova.storage.Storage;
import Nova.tasks.TaskList;
import Nova.ui.Ui;

public class HelpCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showText("""
                Here are the available commands:
                  todo <description>
                  deadline <description> /by <date>
                  event <description> /from <date> /to <date>
                  list
                  schedule <date>
                  mark <task number>
                  unmark <task number>
                  delete <task number>
                  help
                  bye""");
    }

    @Override
    public String getFormat() {
        return "help";
    }
}


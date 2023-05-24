package Bank;

import java.util.ArrayList;
import java.util.List;

public class History {
    private List<Command> commands;

    History() {
        commands = new ArrayList<>();
    }

    public boolean pushBack(Command command) {
        return commands.add(command);
    }
    public Command popBack() {
        return commands.remove(commands.size() - 1);
    }
    public List<Command> getCommandHistory() {
        return commands;
    }
}

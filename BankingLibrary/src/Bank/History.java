package Bank;

import Bank.Commands.Command;

import java.util.ArrayList;
import java.util.List;

public class History {
    List<Command> commandList;

    public History() {
        this.commandList = new ArrayList<>();
    }
    public List<Command> getCommandList() {
        return commandList;
    }
    public void log(Command command) {
        commandList.add(command);
    }
}

package Bank;

import Bank.Commands.Command;
import Bank.Reporting.AccountVisitor;
import Bank.Reporting.CustomerVisitor;
import Bank.Reporting.HistoryVisitor;

import java.util.ArrayList;
import java.util.List;

public class History implements VisitorReceiver {
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

    @Override
    public Account accept(AccountVisitor visitor) {
        return null;
    }
    @Override
    public Customer accept(CustomerVisitor visitor) {
        return null;
    }
    @Override
    public List<Command> accept(HistoryVisitor visitor) {
        return visitor.visit(this);
    }
}

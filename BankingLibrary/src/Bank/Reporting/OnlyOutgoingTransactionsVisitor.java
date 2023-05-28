package Bank.Reporting;

import Bank.Account;
import Bank.Commands.Command;
import Bank.Commands.OutgoingTransferCommand;
import Bank.History;

import java.util.ArrayList;
import java.util.List;

public class OnlyOutgoingTransactionsVisitor implements HistoryVisitor {
    public OnlyOutgoingTransactionsVisitor() { }
    @Override
    public List<Command> visit(History history) {
        List<Command> result = new ArrayList<>();

        for (Command operation : history.getCommandList()) {
            if (operation instanceof OutgoingTransferCommand) result.add(operation);
        }
        return result;
    }
}

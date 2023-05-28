package Bank.Reporting;

import Bank.Commands.Command;

public interface TransactionVisitor {
    Command visit(Command command);
}

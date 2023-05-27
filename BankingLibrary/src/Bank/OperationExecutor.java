package Bank;

import Bank.Commands.Command;

public interface OperationExecutor {
    boolean executeOperation(Command command);
}

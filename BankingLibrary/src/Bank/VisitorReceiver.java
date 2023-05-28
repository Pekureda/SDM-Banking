package Bank;

import Bank.Commands.Command;
import Bank.Reporting.AccountVisitor;
import Bank.Reporting.CustomerVisitor;
import Bank.Reporting.HistoryVisitor;

import java.util.List;

public interface VisitorReceiver {
    Account accept(AccountVisitor visitor);
    Customer accept(CustomerVisitor visitor);
    List<Command> accept(HistoryVisitor visitor);
}

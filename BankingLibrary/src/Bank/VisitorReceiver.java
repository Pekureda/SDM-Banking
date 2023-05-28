package Bank;

import Bank.Commands.Command;
import Bank.Reporting.AccountVisitor;
import Bank.Reporting.CustomerVisitor;
import Bank.Reporting.TransactionVisitor;

public interface VisitorReceiver {
    Account accept(AccountVisitor visitor);
    Command accept(TransactionVisitor visitor);
    Customer accept(CustomerVisitor visitor);
}

package Bank.Commands;

import Bank.Account;
import Bank.Customer;
import Bank.Reporting.AccountVisitor;
import Bank.Reporting.CustomerVisitor;

import java.time.LocalDateTime;

public class DirectPaymentCommand implements Command {
    public final Account recipient;
    public final double amount;
    private LocalDateTime executionTime;

    public DirectPaymentCommand(Account recipient, double amount) {
        this.recipient = recipient;
        this.amount = amount;
    }
    @Override
    public boolean execute() {
        executionTime = LocalDateTime.now();
        recipient.increaseBalance(amount);
        return true;
    }
    public LocalDateTime getExecutionTime() {
        if (executionTime == null) {
            return null;
        }
        return executionTime;
    }
}

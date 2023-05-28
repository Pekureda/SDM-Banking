package Bank.Commands;

import Bank.Account;
import Bank.Customer;
import Bank.Reporting.AccountVisitor;
import Bank.Reporting.CustomerVisitor;

import java.time.LocalDateTime;

public class WithdrawCommand implements Command {
    public final Account target;
    public final double amount;
    private LocalDateTime executionTime;
    public WithdrawCommand(Account target, double amount) {
        this.target = target;
        this.amount = amount;
    }
    @Override
    public boolean execute() {
        executionTime = LocalDateTime.now();
        return target.decreaseBalance(amount);
    }

    public LocalDateTime getExecutionTime() {
        if (executionTime == null) {
            return null;
        }
        return executionTime;
    }
}

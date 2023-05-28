package Bank.Commands;

import Bank.*;
import Bank.Reporting.AccountVisitor;
import Bank.Reporting.CustomerVisitor;
import Bank.Reporting.TransactionVisitor;

import java.time.LocalDateTime;

public class OutgoingTransferCommand implements Command {
    final Account sourceAccount;
    final AccountNumber destinationAccountNumber;
    final double amount;
    final String text;
    private LocalDateTime executionTime;
    OutgoingTransferCommand(Account sourceAccount, AccountNumber destinationAccountNumber, double amount, String text) {
        this.sourceAccount = sourceAccount;
        this.destinationAccountNumber = destinationAccountNumber;
        this.amount = amount;
        this.text = text;
    }
    @Override
    public boolean execute() {
        executionTime = LocalDateTime.now();
        return sourceAccount.decreaseBalance(amount);
    }
    public LocalDateTime getExecutionTime() {
        if (executionTime == null) {
            return null;
        }
        return executionTime;
    }
    @Override
    public Account accept(AccountVisitor visitor) {
        return null;
    }

    @Override
    public Command accept(TransactionVisitor visitor) {
        return visitor.visit(this);
    }
    @Override
    public Customer accept(CustomerVisitor visitor) {
        return null;
    }
}

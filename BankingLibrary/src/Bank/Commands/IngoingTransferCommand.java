package Bank.Commands;

import Bank.*;
import Bank.Reporting.AccountVisitor;
import Bank.Reporting.CustomerVisitor;
import Bank.Reporting.TransactionVisitor;

import java.time.LocalDateTime;

public class IngoingTransferCommand implements Command {
    final Account destination;
    final AccountNumber sourceAccountNumber;
    final double amount;
    final String text;
    private LocalDateTime executionTime;
    public IngoingTransferCommand(AccountNumber sourceAccountNumber, Account destination, double amount, String text) {
        this.destination = destination;
        this.sourceAccountNumber = sourceAccountNumber;
        this.amount = amount;
        this.text = text;
    }
    @Override
    public boolean execute() {
        executionTime = LocalDateTime.now();
        destination.increaseBalance(amount);
        return true;
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

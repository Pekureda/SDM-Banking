package Bank.Commands;

import Bank.*;
import Bank.Reporting.AccountVisitor;
import Bank.Reporting.CustomerVisitor;
import Bank.Reporting.TransactionVisitor;

import java.time.LocalDateTime;

public class InternalTransferCommand implements Command {
    private final Bank bank;
    final Account sourceAccount;
    final Account destinationAccount;
    final double amount;
    final String text;
    private LocalDateTime executionTime;
    public InternalTransferCommand(Bank bank, Account sourceAccount, Account destinationAccount, double amount, String text) {
        this.bank = bank;
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
        this.text = text;
    }
    @Override
    public boolean execute() {
        boolean result = true;
        executionTime = LocalDateTime.now();
        result &= sourceAccount.executeOperation(new OutgoingTransferCommand(sourceAccount, destinationAccount.accountNumber, amount, text));
        result &= destinationAccount.executeOperation(new IngoingTransferCommand(sourceAccount.accountNumber, destinationAccount, amount, text));
        return result;
    }
    public LocalDateTime getExecutionTime() {
        if (executionTime == null) return null;
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

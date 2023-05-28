package Bank.Commands;

import Bank.Bank;
import Bank.*;
import Bank.Reporting.AccountVisitor;
import Bank.Reporting.CustomerVisitor;
import Bank.Reporting.TransactionVisitor;

import java.time.LocalDateTime;

public class CreateLoanCommand implements Command {
    public final Bank bank;
    public final Account owningAccount;
    public final AccountNumber loanNumber;
    public final double amount;
    private LocalDateTime executionTime;
    public CreateLoanCommand(Bank bank, Account owningAccount, AccountNumber loanNumber, double amount) {
        this.bank = bank;
        this.owningAccount = owningAccount;
        this.loanNumber = loanNumber;
        this.amount = amount;
    }
    @Override
    public boolean execute() {
        executionTime = LocalDateTime.now();
        owningAccount.addLoan(new Loan(bank, owningAccount, owningAccount.getOwner(), loanNumber, amount));
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

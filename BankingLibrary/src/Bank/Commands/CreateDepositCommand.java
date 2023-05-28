package Bank.Commands;

import Bank.*;
import Bank.InterestRate.DepositInterestRateStrategy;
import Bank.Reporting.AccountVisitor;
import Bank.Reporting.CustomerVisitor;
import Bank.Reporting.TransactionVisitor;

import java.time.LocalDateTime;

public class CreateDepositCommand implements Command {
    public final Bank bank;
    public final Account owningAccount;
    public final AccountNumber depositNumber;
    public final double amount;
    public final DepositInterestRateStrategy depositInterestRateStrategy;
    private LocalDateTime executionTime;
    public CreateDepositCommand(Bank bank, Account owningAccount, AccountNumber depositNumber, double amount, DepositInterestRateStrategy depositInterestRateStrategy) {
        this.bank = bank;
        this.owningAccount = owningAccount;
        this.depositNumber = depositNumber;
        this.amount = amount;
        this.depositInterestRateStrategy = depositInterestRateStrategy;
    }
    @Override
    public boolean execute() {
        executionTime = LocalDateTime.now();
        owningAccount.addDeposit(new Deposit(bank, owningAccount, owningAccount.getOwner(), depositNumber, amount, depositInterestRateStrategy));
        return false;
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

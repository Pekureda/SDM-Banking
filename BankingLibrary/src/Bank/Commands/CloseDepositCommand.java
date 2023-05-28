package Bank.Commands;

import Bank.*;
import Bank.InterestRate.DepositInterestRateStrategy;
import Bank.Reporting.AccountVisitor;
import Bank.Reporting.CustomerVisitor;
import Bank.Reporting.TransactionVisitor;

import java.time.LocalDateTime;

public class CloseDepositCommand implements Command {
    public final Deposit deposit;
    private LocalDateTime executionTime;
    private double depositReturned;
    public CloseDepositCommand(Deposit deposit) {
        this.deposit = deposit;
    }
    @Override
    public boolean execute() {
        executionTime = LocalDateTime.now();
        depositReturned = deposit.isDue() ? deposit.startingBalance : deposit.getBalance();
        deposit.getOwningBank().transfer(deposit, deposit.getOwningAccount().accountNumber, depositReturned, "DEPOSIT CLOSED: " + deposit.accountNumber);
        deposit.getOwningAccount().removeDeposit(deposit);
        return false;
    }
    public LocalDateTime getExecutionTime() {
        if (executionTime == null) {
            return null;
        }
        return executionTime;
    }

    public double getDepositReturned() {
        return depositReturned;
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

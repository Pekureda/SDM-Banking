package Bank.Commands;

import Bank.*;
import Bank.InterestRate.DepositInterestRateStrategy;

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
}

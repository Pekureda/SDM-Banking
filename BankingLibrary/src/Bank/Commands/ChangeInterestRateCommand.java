package Bank.Commands;

import Bank.Account;
import Bank.Deposit;
import Bank.InterestRate.AccountInterestRateStrategy;
import Bank.InterestRate.DepositInterestRateStrategy;
import Bank.InterestRate.InterestRateStrategy;
import Bank.InterestRate.LoanInterestRateStrategy;
import Bank.Loan;

import java.time.LocalDateTime;

public class ChangeInterestRateCommand implements Command {
    public final Account interestRateChangeTarget;
    public final InterestRateStrategy interestRateStrategy;
    private LocalDateTime executionTime;
    public ChangeInterestRateCommand(Account interestRateChangeTarget, InterestRateStrategy interestRateStrategy) {
        this.interestRateChangeTarget = interestRateChangeTarget;
        this.interestRateStrategy = interestRateStrategy;
    }
    @Override
    public boolean execute() {
        executionTime = LocalDateTime.now();
        return interestRateChangeTarget.setInterestRateStrategy((interestRateStrategy));
    }

    public LocalDateTime getExecutionTime() {
        if (executionTime == null) {
            return null;
        }
        return executionTime;
    }
}

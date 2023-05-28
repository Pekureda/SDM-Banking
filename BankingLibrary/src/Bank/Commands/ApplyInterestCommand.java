package Bank.Commands;

import Bank.*;
import Bank.InterestRate.InterestRateStrategy;
import Bank.Reporting.AccountVisitor;
import Bank.Reporting.CustomerVisitor;
import Bank.Reporting.TransactionVisitor;

import java.time.LocalDateTime;

public class ApplyInterestCommand implements Command {
    public final InterestRateApplicableProduct product;
    public final InterestRateStrategy strategy;
    protected Double interest;
    protected LocalDateTime executionTime;
    public ApplyInterestCommand(InterestRateApplicableProduct product, InterestRateStrategy strategy) {
        this.product = product;
        this.strategy = strategy;
    }
    @Override
    public boolean execute() {
        executionTime = LocalDateTime.now();
        interest = strategy.applyInterest(product);
        return true;
    }
    public LocalDateTime getExecutionTime() {
        if (executionTime == null) {
            return null;
        }
        return executionTime;
    }

    public Double getInterest() {
        if (interest == null) {
            return null;
        }
        return interest;
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

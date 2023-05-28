package Bank.InterestRate;

import Bank.InterestRateApplicableProduct;

import java.time.LocalDate;

public class SimpleDepositInterestRateStrategy implements DepositInterestRateStrategy {
    public final double percentage;
    public final LocalDate dueDate;
    public SimpleDepositInterestRateStrategy(double percentage, LocalDate dueDate) {
        this.percentage = percentage;
        this.dueDate = dueDate;
    }
    @Override
    public double applyInterest(InterestRateApplicableProduct product) {
        double interest = product.getBalance() * percentage / 100;
        product.increaseBalance(interest);
        return interest;
    }
    @Override
    public boolean isDue() {
        return LocalDate.now().isBefore(dueDate);
    }
}

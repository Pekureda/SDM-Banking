package Bank.InterestRate;

import Bank.InterestRateApplicableProduct;

import java.time.LocalDate;

public class SimpleLoanInterestRateStrategy implements LoanInterestRateStrategy {
    public final double percentage;
    public SimpleLoanInterestRateStrategy(double percentage) {
        this.percentage = percentage;
    }
    @Override
    public double applyInterest(InterestRateApplicableProduct product) {
        double interest = product.getBalance() * percentage / 100;
        product.decreaseBalance(interest);
        return interest;
    }
}

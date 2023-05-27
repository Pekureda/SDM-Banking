package Bank.InterestRate;

import Bank.InterestRateApplicableProduct;

public class SimpleDepositInterestRateStrategy implements DepositInterestRateStrategy {
    public final double percentage;
    public SimpleDepositInterestRateStrategy(double percentage) {
        this.percentage = percentage;
    }
    @Override
    public double applyInterest(InterestRateApplicableProduct product) {
        double interest = product.getBalance() * percentage;
        product.increaseBalance(interest);
        return interest;
    }
}

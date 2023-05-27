package Bank.InterestRate;

import Bank.InterestRateApplicableProduct;

public class SimpleAccountInterestRateStrategy implements AccountInterestRateStrategy {
    public final double percentage;
    public SimpleAccountInterestRateStrategy(double percentage) {
        this.percentage = percentage;
    }
    @Override
    public double applyInterest(InterestRateApplicableProduct product) {
        double interest = product.getBalance() * percentage;
        if (interest > product.getBalance()) interest = product.getBalance();
        product.decreaseBalance(interest);
        return interest;
    }
}

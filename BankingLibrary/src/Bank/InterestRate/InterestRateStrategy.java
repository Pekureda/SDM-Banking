package Bank.InterestRate;

import Bank.InterestRateApplicableProduct;

public interface InterestRateStrategy {
    double applyInterest(InterestRateApplicableProduct product);
}

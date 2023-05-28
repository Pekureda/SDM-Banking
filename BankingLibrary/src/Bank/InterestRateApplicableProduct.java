package Bank;

import Bank.InterestRate.InterestRateStrategy;

import java.time.LocalDateTime;

public interface InterestRateApplicableProduct {
    double getBalance();
    LocalDateTime getDateOfOpening();
    History getHistory();
    boolean applyInterest();
    boolean decreaseBalance(double amount);
    boolean increaseBalance(double amount);
}

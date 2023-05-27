package Bank;

import Bank.InterestRate.AccountInterestRateStrategy;
import Bank.InterestRate.DepositInterestRateStrategy;

public class Deposit extends Account {
    private final Account owningAccount;
    public final double startingBalance;

    public Deposit(Bank owningBank, Account owningAccount, Customer owner, AccountNumber accountNumber, double depositAmount, DepositInterestRateStrategy depositInterestRateStrategy) {
        super(owningBank, owner, accountNumber, depositAmount);
        this.startingBalance = depositAmount;
        owningBank.transfer(owningAccount, accountNumber, depositAmount, "DEPOSIT: " + accountNumber.toString());
        this.owningAccount = owningAccount;
        this.interestRateStrategy = depositInterestRateStrategy;
    }

    @Override
    public boolean increaseBalance(double amount) {
        balance += Math.abs(amount);
        return true;
    }
    @Override
    public boolean decreaseBalance(double amount) {
        if (balance == Math.abs(amount)) {
            balance -= Math.abs(amount);
            return true;
        }
        return false;
    }
    @Override
    public boolean addDeposit(Deposit none) {
        return false;
    }
    @Override
    public boolean addLoan(Loan none) {
        return false;
    }
    public boolean isDue() {
        assert interestRateStrategy instanceof DepositInterestRateStrategy;
        return ((DepositInterestRateStrategy)interestRateStrategy).isDue();
    }
    public Account getOwningAccount() {
        return owningAccount;
    }
}

package Bank;

import Bank.Commands.InternalTransferCommand;
import Bank.InterestRate.AccountInterestRateStrategy;
import Bank.InterestRate.DepositInterestRateStrategy;

import java.util.List;

public class Deposit extends Account {
    private final Account owningAccount;
    public final double startingBalance;

    public Deposit(Bank owningBank, Account owningAccount, Customer owner, AccountNumber accountNumber, double depositAmount, DepositInterestRateStrategy depositInterestRateStrategy) {
        super(owningBank, owner, accountNumber, depositInterestRateStrategy, depositAmount);
        this.startingBalance = depositAmount;
        this.balance = 0.0;
        assert owningBank.executeOperation(new InternalTransferCommand(owningBank, owningAccount, this, depositAmount, "DEPOSIT: " + accountNumber.toString()));
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
    @Override
    public List<Loan> getLoans() {
        return null;
    }
    @Override
    public List<Deposit> getDeposits() {
        return null;
    }
    public boolean isDue() {
        assert interestRateStrategy instanceof DepositInterestRateStrategy;
        return ((DepositInterestRateStrategy)interestRateStrategy).isDue();
    }
    public Account getOwningAccount() {
        return owningAccount;
    }
    public double getStartingBalance() {
        return startingBalance;
    }
}

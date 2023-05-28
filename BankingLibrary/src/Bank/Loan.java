package Bank;

import Bank.InterestRate.LoanInterestRateStrategy;

import java.util.List;

public class Loan extends Account {
    private final Account owningAccount;

    public Loan(Bank owningBank, Account owningAccount, Customer owner, AccountNumber accountNumber, double borrowAmount, LoanInterestRateStrategy loanInterestRateStrategy) {
        super(owningBank, owner, accountNumber, loanInterestRateStrategy, Math.abs(borrowAmount));
        owningBank.transfer(this, owningAccount.accountNumber, borrowAmount, "LOAN: " + accountNumber.toString());
        balance = -Math.abs(borrowAmount);
        this.owningAccount = owningAccount;
    }

    @Override
    public boolean increaseBalance(double amount) {
        balance += Math.abs(amount);
        return balance >= 0;
    }
    @Override
    public boolean decreaseBalance(double amount) {
        balance -= Math.abs(amount);
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
}

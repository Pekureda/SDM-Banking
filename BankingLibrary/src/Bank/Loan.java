package Bank;

public class Loan extends Account {
    private final Account owningAccount;

    public Loan(Bank owningBank, Account owningAccount, Customer owner, AccountNumber accountNumber, double borrowAmount) {
        super(owningBank, owner, accountNumber, Math.abs(borrowAmount));
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
}

package Bank;

public class Deposit extends Account {
    private final Account owningAccount;

    public Deposit(Bank owningBank, Account owningAccount, Customer owner, AccountNumber accountNumber, double depositAmount) {
        super(owningBank, owner, accountNumber, depositAmount);
        owningBank.transfer(owningAccount, accountNumber, depositAmount, "DEPOSIT: " + accountNumber.toString());
        this.owningAccount = owningAccount;
    }

    @Override
    public boolean increaseBalance(double amount) {
        return false;
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
}

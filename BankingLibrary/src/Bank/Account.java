package Bank;

import Bank.Commands.ApplyInterestCommand;
import Bank.Commands.Command;
import Bank.InterestRate.AccountInterestRateStrategy;
import Bank.InterestRate.InterestRateStrategy;
import Bank.InterestRate.SimpleDepositInterestRateStrategy;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Account implements InterestRateApplicableProduct, OperationExecutor {
    public final AccountNumber accountNumber;
    protected Customer owner;
    protected Bank owningBank;
    protected Double balance;
    protected LocalDateTime dateOfOpening;
    protected History operationHistory;
    private Map<String, Deposit> depositMap;
    private Map<String, Loan> loanMap;
    protected InterestRateStrategy interestRateStrategy;
    Account(Bank owningBank, Customer owner, AccountNumber accountNumber) {
        this(owningBank, owner, accountNumber, 0.0);
    }
    Account(Bank owningBank, Customer owner, AccountNumber accountNumber, double startingBalance) {
        this.owningBank = owningBank;
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = startingBalance;
        this.operationHistory = new History();
        this.dateOfOpening = LocalDateTime.now();
        this.depositMap = new HashMap<>();
        this.loanMap = new HashMap<>();
    }
    public Customer getOwner() {
        return owner;
    }
    public double getBalance() {
        return balance;
    }
    public LocalDateTime getDateOfOpening() {
        return dateOfOpening;
    }

    public History getHistory() {
        return operationHistory;
    }

    public boolean increaseBalance(double amount) {
        balance += Math.abs(amount);
        return true;
    }
    public boolean decreaseBalance(double amount) {
        if (balance - Math.abs(amount) < 0) return false;
        balance -= Math.abs(amount);
        return true;
    }
    public boolean executeOperation(Command command) {
        boolean result = command.execute();
        operationHistory.log(command);
        owningBank.logOperation(command);
        return result;
    }
    public boolean addDeposit(Deposit deposit) {
        depositMap.put(deposit.accountNumber.getInBankAccountNumber(), deposit);
        return true;
    }
    public boolean addLoan(Loan loan) {
        loanMap.put(loan.accountNumber.getInBankAccountNumber(), loan);
        return true;
    }
    public boolean applyInterest() {
        return executeOperation(new ApplyInterestCommand(this, interestRateStrategy));
    }
    public void setInterestRateStrategy(AccountInterestRateStrategy accountInterestRateStrategy) {
        this.interestRateStrategy = accountInterestRateStrategy;
    }
}

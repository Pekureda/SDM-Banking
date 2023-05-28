package Bank;

import Bank.Commands.ApplyInterestCommand;
import Bank.Commands.Command;
import Bank.InterestRate.AccountInterestRateStrategy;
import Bank.InterestRate.InterestRateStrategy;
import Bank.InterestRate.SimpleDepositInterestRateStrategy;
import Bank.Reporting.AccountVisitor;
import Bank.Reporting.CustomerVisitor;
import Bank.Reporting.HistoryVisitor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Account implements InterestRateApplicableProduct, OperationExecutor, VisitorReceiver {
    public final AccountNumber accountNumber;
    protected Customer owner;
    protected Bank owningBank;
    protected Double balance;
    private boolean isDebit;
    private double overdraftLimit;
    protected LocalDateTime dateOfOpening;
    protected History operationHistory;
    private Map<String, Deposit> depositMap;
    private Map<String, Loan> loanMap;
    protected InterestRateStrategy interestRateStrategy;
    Account(Bank owningBank, Customer owner, AccountNumber accountNumber, AccountInterestRateStrategy accountInterestRateStrategy) {
        this(owningBank, owner, accountNumber, accountInterestRateStrategy, 0.0);
    }
    Account(Bank owningBank, Customer owner, AccountNumber accountNumber, InterestRateStrategy interestRateStrategy, double startingBalance) {
        this.owningBank = owningBank;
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = startingBalance;
        this.operationHistory = new History();
        this.dateOfOpening = LocalDateTime.now();
        this.depositMap = new HashMap<>();
        this.loanMap = new HashMap<>();
        this.interestRateStrategy = interestRateStrategy;
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
    public Bank getOwningBank() {
        return owningBank;
    }

    public boolean increaseBalance(double amount) {
        balance += Math.abs(amount);
        return true;
    }
    public boolean decreaseBalance(double amount) {
        if (balance - Math.abs(amount) < (isDebit ? overdraftLimit : 0.0)) return false;
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
    public boolean removeDeposit(Deposit deposit) {
        return depositMap.remove(deposit.accountNumber.getInBankAccountNumber()) != null;
    }
    public boolean addLoan(Loan loan) {
        loanMap.put(loan.accountNumber.getInBankAccountNumber(), loan);
        return true;
    }
    public boolean setDebit(boolean state, double overdraftLimit) {
        this.isDebit = state;
        this.overdraftLimit = overdraftLimit;
        return true;
    }
    public List<Loan> getLoans() {
        return loanMap.values().stream().toList();
    }
    public List<Deposit> getDeposits() {
        return depositMap.values().stream().toList();
    }
    public boolean applyInterest() {
        return executeOperation(new ApplyInterestCommand(this, interestRateStrategy));
    }
    public boolean setInterestRateStrategy(InterestRateStrategy interestRateStrategy) {
        if (!(interestRateStrategy instanceof AccountInterestRateStrategy))  return false;
        this.interestRateStrategy = interestRateStrategy;
        return true;
    }

    @Override
    public Account accept(AccountVisitor visitor) {
        return visitor.visit(this);
    }
    @Override
    public Customer accept(CustomerVisitor visitor) {
        return null;
    }
    @Override
    public List<Command> accept(HistoryVisitor visitor) {
        return getHistory().accept(visitor);
    }
}

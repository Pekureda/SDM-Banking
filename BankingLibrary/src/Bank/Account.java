package Bank;

import Bank.Commands.Command;

import java.time.LocalDateTime;

public class Account {
    public final AccountNumber accountNumber;
    private Customer owner;
    private Bank owningBank;
    private double balance;
    private LocalDateTime dateOfOpening;
    private History operationHistory;
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
    }
    Customer getOwner() {
        return owner;
    }
    public double getBalance() {
        return balance;
    }
    LocalDateTime getDateOfOpening() {
        return dateOfOpening;
    }
    public void increaseBalance(double amount) {
        balance += Math.abs(amount);
    }
    public boolean decreaseBalance(double amount) {
        if (balance - Math.abs(amount) < 0) return false;
        balance -= Math.abs(amount);
        return true;
    }
    public boolean executeOperation(Command command) {
        command.execute();
        operationHistory.log(command);
        owningBank.logOperation(command);
        return true;
    }
}

package Bank.Commands;

import Bank.*;

import java.time.LocalDateTime;

public class CreateDepositCommand implements Command {
    public final Bank bank;
    public final Account owningAccount;
    public final AccountNumber depositNumber;
    public final double amount;
    private LocalDateTime executionTime;
    public CreateDepositCommand(Bank bank, Account owningAccount, AccountNumber depositNumber, double amount) {
        this.bank = bank;
        this.owningAccount = owningAccount;
        this.depositNumber = depositNumber;
        this.amount = amount;
    }
    @Override
    public boolean execute() {
        executionTime = LocalDateTime.now();
        owningAccount.addDeposit(new Deposit(bank, owningAccount, owningAccount.getOwner(), depositNumber, amount));
        return false;
    }
    public LocalDateTime getExecutionTime() {
        if (executionTime == null) {
            return null;
        }
        return executionTime;
    }
}

package Bank.Commands;

import Bank.*;

import java.time.LocalDateTime;

public class IncomingExternalTransferCommand implements Command {
    public final Bank bank;
    public final AccountNumber sourceAccountNumber;
    public final AccountNumber destinationAccountNumber;
    public final double amount;
    public final String text;
    private LocalDateTime executionTime;
    public IncomingExternalTransferCommand(Bank bank, AccountNumber sourceAccountNumber, AccountNumber destinationAccountNumber, double amount, String text) {
        this.bank = bank;
        this.sourceAccountNumber = sourceAccountNumber;
        this.destinationAccountNumber = destinationAccountNumber;
        this.amount = amount;
        this.text = text;
    }
    @Override
    public boolean execute() {
        executionTime = LocalDateTime.now();
        Account destinationAccount = bank.getAccountByNumber(destinationAccountNumber);
        if (destinationAccount == null) {
            // todo return operation NO_SUCH_ACCOUNT
        }
        else {
            bank.executeOperation(new IngoingTransferCommand(sourceAccountNumber, destinationAccount, amount, text));
        }
        return false;
    }

    public LocalDateTime getExecutionTime() {
        if (executionTime == null) {
            return null;
        }
        return executionTime;
    }
}

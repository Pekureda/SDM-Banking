package Bank.Commands;

import Bank.*;

import java.time.LocalDateTime;

public class OutgoingExternalTransferCommand implements Command {
    protected final InterbankPaymentSystemMediator ibp;
    protected final Bank bank;
    public final Account sourceAccount;
    public final AccountNumber destinationAccountNumber;
    public final double amount;
    public final String text;
    protected LocalDateTime executionTime;
    public OutgoingExternalTransferCommand(Bank bank, InterbankPaymentSystemMediator ibp, Account sourceAccount, AccountNumber destinationAccountNumber, double amount, String text) {
        this.bank = bank;
        this.ibp = ibp;
        this.sourceAccount = sourceAccount;
        this.destinationAccountNumber = destinationAccountNumber;
        this.amount = amount;
        this.text = text;
    }
    @Override
    public boolean execute() {
        executionTime = LocalDateTime.now();
        sourceAccount.executeOperation(new OutgoingTransferCommand(sourceAccount, destinationAccountNumber, amount, text));
        ibp.notify(bank, new InterbankTransfer(sourceAccount.accountNumber, destinationAccountNumber, amount, text, InterbankTransferType.INCOMING_TRANSFER));
        return true;
    }
    public LocalDateTime getExecutionTime() {
        if (executionTime == null) {
            return null;
        }
        return executionTime;
    }
}

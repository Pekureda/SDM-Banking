package Bank.Commands;

import Bank.*;

import java.time.LocalDateTime;

public class OutgoingExternalTransferCommand implements Command {
    private final InterbankPaymentSystemMediator ibp;
    private final Bank bank;
    public final AccountNumber sourceAccountNumber;
    public final AccountNumber destinationAccountNumber;
    public final double amount;
    public final String text;
    private LocalDateTime executionTime;
    OutgoingExternalTransferCommand(Bank bank, InterbankPaymentSystemMediator ibp, AccountNumber sourceAccountNumber, AccountNumber destinationAccountNumber, double amount, String text) {
        this.bank = bank;
        this.ibp = ibp;
        this.sourceAccountNumber = sourceAccountNumber;
        this.destinationAccountNumber = destinationAccountNumber;
        this.amount = amount;
        this.text = text;
    }
    @Override
    public boolean execute() {
        executionTime = LocalDateTime.now();
        ibp.notify(bank, new InterbankTransfer(sourceAccountNumber, destinationAccountNumber, amount, text, InterbankTransferType.INCOMING_TRANSFER));
        return true;
    }
    public LocalDateTime getExecutionTime() {
        if (executionTime == null) {
            return null;
        }
        return executionTime;
    }
}

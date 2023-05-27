package Bank.Commands;

import Bank.*;

import java.time.LocalDateTime;

public class OutgoingReturningExternalTransferCommand implements Command {
    protected final InterbankPaymentSystemMediator ibp;
    protected final Bank bank;
    public final AccountNumber sourceAccountNumber;
    public final AccountNumber destinationAccountNumber;
    public final double amount;
    public final String text;
    protected LocalDateTime executionTime;
    OutgoingReturningExternalTransferCommand(Bank bank, InterbankPaymentSystemMediator ibp, AccountNumber sourceAccountNumber, AccountNumber destinationAccountNumber, double amount, String text) {
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
        ibp.notify(bank, new InterbankTransfer(sourceAccountNumber, destinationAccountNumber, amount, text, InterbankTransferType.RETURNING_TRANSFER_NO_SUCH_ACCOUNT));
        return true;
    }
}

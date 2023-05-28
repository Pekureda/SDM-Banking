package Bank.Commands;

import Bank.*;
import Bank.Reporting.AccountVisitor;
import Bank.Reporting.CustomerVisitor;

import java.time.LocalDateTime;
import java.util.Objects;

public class IncomingExternalTransferCommand implements Command {
    public final InterbankPaymentSystemMediator ibp;
    public final Bank bank;
    public final AccountNumber sourceAccountNumber;
    public final AccountNumber destinationAccountNumber;
    public final double amount;
    public final String text;
    protected LocalDateTime executionTime;
    public IncomingExternalTransferCommand(Bank bank, InterbankPaymentSystemMediator ibp, AccountNumber sourceAccountNumber, AccountNumber destinationAccountNumber, double amount, String text) {
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

        Account destinationAccount = bank.getAccountByNumber(destinationAccountNumber);
        if (destinationAccount == null) {
            bank.executeOperation(new OutgoingReturningExternalTransferCommand(bank, ibp, sourceAccountNumber, destinationAccountNumber, amount, text));
        }
        else {
            destinationAccount.executeOperation(new IngoingTransferCommand(sourceAccountNumber, destinationAccount, amount, text));
        }
        return true;
    }

    public LocalDateTime getExecutionTime() {
        if (executionTime == null) {
            return null;
        }
        return executionTime;
    }
}

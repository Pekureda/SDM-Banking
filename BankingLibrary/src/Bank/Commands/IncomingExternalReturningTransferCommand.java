package Bank.Commands;

import Bank.*;

import java.time.LocalDateTime;

public class IncomingExternalReturningTransferCommand extends IncomingExternalTransferCommand {
    public IncomingExternalReturningTransferCommand(Bank bank, InterbankPaymentSystemMediator ibp, AccountNumber sourceAccountNumber, AccountNumber destinationAccountNumber, double amount, String text) {
        super(bank, ibp, sourceAccountNumber, destinationAccountNumber, amount, text);
    }

    @Override
    public boolean execute() {
        executionTime = LocalDateTime.now();

        Account sourceAccount = bank.getAccountByNumber(sourceAccountNumber);
        sourceAccount.executeOperation(new IngoingTransferCommand(destinationAccountNumber, sourceAccount, amount, text));

        return true;
    }
}

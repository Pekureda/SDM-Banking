package Bank.Commands;

import Bank.*;

public class IncomingExternalReturningTransferCommand extends IncomingExternalTransferCommand {
    public IncomingExternalReturningTransferCommand(Bank bank, InterbankPaymentSystemMediator ibp, AccountNumber sourceAccountNumber, AccountNumber destinationAccountNumber, double amount, String text) {
        super(bank, ibp, sourceAccountNumber, destinationAccountNumber, amount, text);
    }
}

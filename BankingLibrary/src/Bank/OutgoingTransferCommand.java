package Bank;

import java.util.Currency;

public final class OutgoingTransferCommand extends OrderTransferCommand {
    OutgoingTransferCommand(Bank bank, Account sourceAccount, double amount, Currency currency, String recipientAccountId, String text) {
        super(bank, sourceAccount, amount, currency, recipientAccountId, text);
    }
    @Override
    public boolean execute() {
        try {
            sourceAccount.updateBalance(-amount);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

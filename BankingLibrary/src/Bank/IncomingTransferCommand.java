package Bank;

import java.util.Currency;

public final class IncomingTransferCommand extends OrderTransferCommand {
    IncomingTransferCommand(Bank bank, Account sourceAccount, double amount, Currency currency, String recipientAccountId, String text) {
        super(bank, sourceAccount, amount, currency, recipientAccountId, text);
    }
    @Override
    public boolean execute() {
        Account recipientAccount = bank.getAccountById(recipientAccountId);
        if (recipientAccount != null) {
            try {
                recipientAccount.updateBalance(amount);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        // Should never reach this point
        assert(false);
        return false;
    }
}

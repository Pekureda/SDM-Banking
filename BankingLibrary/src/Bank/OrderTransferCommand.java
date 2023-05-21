package Bank;

import java.time.LocalDateTime;
import java.util.Currency;

public class OrderTransferCommand implements Command {
    OrderTransferCommand(Bank bank, Account sourceAccount, double amount, Currency currency, String recipientAccountId, String text){
        this.bank = bank;
        this.sourceAccount = sourceAccount;
        this.amount = amount;
        this.currency = currency;
        this.recipientAccountId = recipientAccountId;
        this.text = text;
        this.executionTime = LocalDateTime.now();
    }
    protected final Bank bank;
    protected final Account sourceAccount;
    protected final double amount;
    protected final Currency currency;
    protected final String recipientAccountId;
    protected final String text;
    protected final LocalDateTime executionTime;
    @Override
    public boolean execute() {
        if (recipientAccountId.substring(0, bank.bankCode.length()).equals(bank.bankCode)) {
           Account recipientAccount = bank.getAccountById(recipientAccountId);
           if (recipientAccount != null) {
               sourceAccount.executeCommand(new OutgoingTransferCommand(bank, sourceAccount, amount, currency,recipientAccountId, text));
               recipientAccount.executeCommand(new IncomingTransferCommand(bank, sourceAccount, amount, currency,recipientAccountId, text));
               return true;
           }
        }
        else {
            sourceAccount.executeCommand(new OutgoingTransferCommand(bank, sourceAccount, amount, currency,recipientAccountId, text));
            bank.interbankMediator.transferNotify(bank, new ExternalTransfer(sourceAccount.getId(), recipientAccountId, text, amount, currency));
            return true;
        }
        // Order rejected
        return false;
    }

    @Override
    public LocalDateTime getExecutionTime() {
        return executionTime;
    }
}

package Bank;

import java.time.LocalDate;
import java.util.Currency;

public class Account {
    private static long idCounter=0;
    private String accountId;
    private Customer owner;
    private LocalDate opening;
    private double balance;
    private Currency currency;
    private History history;
    Account(Customer owner, double startingBalance, Currency currency) {
        this(owner);
        this.balance = startingBalance;
        this.currency = currency;
    }
    Account(Customer owner){
        accountId = String.format(owner.getBankRef().bankCode + "%010d", idCounter++);
        this.owner = owner;
        opening = LocalDate.now();
        balance = 0.0;
        history = new History();
    }

    public double getBalance() {
        return balance;
    }
    void updateBalance(double delta) throws Exception{
        if (balance + delta < 0.0)
            throw new Exception("Update would result in negative balance");
        balance = balance+delta;
    }

    public String getId() {
        return accountId;
    }

    Customer getOwner() {
        return owner;
    }

    boolean executeCommand(Command command) {
        if (command.execute()) {
            // TODO: 21/05/2023 Put into history
            // TODO: 21/05/2023 Put into bankHistory
            return true;
        }
        return false;
    }

    public boolean orderTransfer(double amount, Currency currency, String recipientAccountId, String text) {
        return owner.getBankRef().executeCommand(new OrderTransferCommand(owner.getBankRef(), this, amount, currency, recipientAccountId, text));
    }
}
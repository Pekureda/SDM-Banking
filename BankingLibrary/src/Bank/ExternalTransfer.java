package Bank;

import java.time.LocalDateTime;
import java.util.Currency;

public class ExternalTransfer {
    ExternalTransfer(String sourceAccountId, String recipientAccountId, String text, double amount, Currency currency) {
        this.sourceAccountId = sourceAccountId;
        this.recipientAccountId = recipientAccountId;
        this.text = text;
        this.amount = amount;
        this.currency = currency;
        this.executionTime = LocalDateTime.now();
    }

    private final String sourceAccountId;
    private final String recipientAccountId;
    private final String text;
    private final double amount;
    private final Currency currency;
    private final LocalDateTime executionTime;

    public Currency getCurrency() {
        return currency;
    }
    public double getAmount() {
        return amount;
    }
    public String getText() {
        return text;
    }
    public LocalDateTime getExecutionTime() {
        return executionTime;
    }
    public String getSourceBankId() {
        return sourceAccountId.substring(0, 4);
    }
    public String getRecipientBankId() {
        return recipientAccountId.substring(0, 4);
    }
    public String getSourceAccountId() {
        return sourceAccountId;
    }
    public String getRecipientAccountId() {
        return recipientAccountId;
    }
}

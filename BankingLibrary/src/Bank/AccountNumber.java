package Bank;

public class AccountNumber {
    private String inBankAccountNumber;
    private String bankIdentifier;

    public AccountNumber(String bankIdentifier, String inBankAccountNumber) {
        this.bankIdentifier = bankIdentifier;
        this.inBankAccountNumber = inBankAccountNumber;
    }
    public AccountNumber(String accountNumber) {
        this.bankIdentifier = accountNumber.substring(0, 4);
        this.inBankAccountNumber = accountNumber.substring(4);
    }
    @Override
    public String toString() {
        return bankIdentifier + inBankAccountNumber;
    }
    public String getInBankAccountNumber() {
        return inBankAccountNumber;
    }
    public String getBankIdentifier() {
        return bankIdentifier;
    }
}

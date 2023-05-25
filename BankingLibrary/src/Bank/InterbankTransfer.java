package Bank;

public record InterbankTransfer(AccountNumber sourceAccountNumber, AccountNumber destinationAccountNumber, double amount, String text, InterbankTransferType type) {
}

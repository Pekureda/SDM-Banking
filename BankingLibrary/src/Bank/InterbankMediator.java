package Bank;

public interface InterbankMediator {
    boolean transferNotify(Bank bank, ExternalTransfer command);
}

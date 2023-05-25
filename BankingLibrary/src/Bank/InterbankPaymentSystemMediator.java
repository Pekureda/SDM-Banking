package Bank;

import Bank.Commands.Command;

public interface InterbankPaymentSystemMediator {
    void registerBank(Bank bank);
    void unregisterBank(Bank bank);
    void notify(Bank bank, InterbankTransfer transfer);
    void receiveTransfers(Bank bank);
}

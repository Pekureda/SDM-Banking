package Bank;

import Bank.*;
import Bank.Commands.IncomingExternalTransferCommand;
import Bank.Commands.IngoingTransferCommand;

import java.util.*;

public class InterbankPaymentSystem implements InterbankPaymentSystemMediator {
    Map<Bank, List<InterbankTransfer>> banks;
    History history;
    InterbankPaymentSystem() {
        this.banks = new HashMap<>();
        this.history = new History();
    }
    @Override
    public void registerBank(Bank bank) {
        if (!banks.containsKey(bank)) {
            banks.put(bank, new ArrayList<>());
        }
    }

    @Override
    public void unregisterBank(Bank bank) {
        if (banks.containsKey(bank)) {
            if (banks.get(bank).isEmpty()) {
                banks.remove(bank);
            }
            else {
                // todo execute transactions??
            }
        }
    }

    @Override
    public void notify(Bank bank, InterbankTransfer transfer) {
        if (banks.containsKey(bank)) {
            if (banks.keySet().stream().noneMatch(b -> Objects.equals(b.bankCode, transfer.destinationAccountNumber().getBankIdentifier()))) {
                InterbankTransfer changedTransfer = new InterbankTransfer(null, transfer.sourceAccountNumber(), transfer.amount(), transfer.text(), InterbankTransferType.RETURNING_TRANSFER_NO_SUCH_BANK);
                assert Objects.equals(bank.bankCode, changedTransfer.destinationAccountNumber().getBankIdentifier()); // Sanity check
                banks.get(bank).add(changedTransfer);
            }
            else {
                banks.get(bank).add(transfer);
            }
        }
    }

    @Override
    public void receiveTransfers(Bank bank) {
        if (banks.containsKey(bank)) {
            for (var transaction : banks.get(bank)) {
                switch (transaction.type()) {
                    case INCOMING_TRANSFER -> {
                        bank.executeOperation(new IncomingExternalTransferCommand(bank, transaction.sourceAccountNumber(), transaction.destinationAccountNumber(), transaction.amount(), transaction.text()));
                    }
                    case RETURNING_TRANSFER_NO_SUCH_ACCOUNT -> {
                        // todo
                    }
                    case RETURNING_TRANSFER_NO_SUCH_BANK -> {
                        // todo
                    }
                }
            }
        }
    }

}

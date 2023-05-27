package Bank;

import Bank.*;
import Bank.Commands.IncomingExternalReturningTransferCommand;
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
            if (!banks.get(bank).isEmpty()) {
                // todo execute transactions
                receiveTransfers(bank);
            }
            banks.remove(bank);
        }
    }

    @Override
    public void notify(Bank bank, InterbankTransfer transfer) {
        if (banks.containsKey(bank)) {
            String destinationBankIdentifier = switch (transfer.type()) {
                case INCOMING_TRANSFER -> transfer.destinationAccountNumber().getBankIdentifier();
                case RETURNING_TRANSFER_NO_SUCH_ACCOUNT, RETURNING_TRANSFER_NO_SUCH_BANK -> transfer.sourceAccountNumber().getBankIdentifier();
            };

            Optional<Bank> destinationBank = banks.keySet().stream().filter(b -> Objects.equals(b.bankCode, destinationBankIdentifier)).findFirst();
            if (destinationBank.isEmpty()) {
                InterbankTransfer changedTransfer = new InterbankTransfer(transfer.sourceAccountNumber(), transfer.destinationAccountNumber(), transfer.amount(), transfer.text(), InterbankTransferType.RETURNING_TRANSFER_NO_SUCH_BANK);
                assert Objects.equals(bank.bankCode, changedTransfer.sourceAccountNumber().getBankIdentifier()) && changedTransfer.type() == InterbankTransferType.RETURNING_TRANSFER_NO_SUCH_BANK; // Sanity check
                banks.get(bank).add(changedTransfer);
            }
            else {
                banks.get(destinationBank.get()).add(transfer);
            }
        }
    }
    @Override
    public void receiveTransfers(Bank bank) {
        if (banks.containsKey(bank)) {
            for (var transaction : banks.get(bank)) {
                switch (transaction.type()) {
                    case INCOMING_TRANSFER -> {
                        bank.executeOperation(new IncomingExternalTransferCommand(bank, this, transaction.sourceAccountNumber(), transaction.destinationAccountNumber(), transaction.amount(), transaction.text()));
                    }
                    case RETURNING_TRANSFER_NO_SUCH_ACCOUNT -> {
                        bank.executeOperation(new IncomingExternalReturningTransferCommand(bank, this, transaction.sourceAccountNumber(), transaction.destinationAccountNumber(), transaction.amount(), transaction.text()));
                    }
                    case RETURNING_TRANSFER_NO_SUCH_BANK -> {
                        bank.executeOperation(new IncomingExternalReturningTransferCommand(bank, this, transaction.sourceAccountNumber(), transaction.destinationAccountNumber(), transaction.amount(), transaction.text()));
                    }
                }
            }
        }
    }

}

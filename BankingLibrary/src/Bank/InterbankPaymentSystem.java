package Bank;

import java.util.*;

public class InterbankPaymentSystem implements InterbankMediator {

    public InterbankPaymentSystem() {
        this.bankMap = new HashMap<>();
        this.pendingTransfers = new HashMap<>();
    }
    private Map<String, Bank> bankMap;
    private Map<String, List<ExternalTransfer>> pendingTransfers;

    @Override
    public boolean transferNotify(Bank bank, ExternalTransfer transfer) {
        var recipientBankCode = transfer.getRecipientBankId();

        if (bank.bankCode.equals(transfer.getRecipientBankId())) {
            recipientBankCode = transfer.getSourceBankId();
        }

        if (!bankMap.containsKey(recipientBankCode)) return false;

        if (pendingTransfers.containsKey(recipientBankCode)) {
            pendingTransfers.get(recipientBankCode).add(transfer);
        }
        else {
            pendingTransfers.put(recipientBankCode, List.of(transfer));
        }

        return true;
    }
    public boolean registerBank(Bank bank) {
        if (bankMap.containsKey(bank.bankCode)) return false;

        bankMap.put(bank.bankCode, bank);
        bank.setInterbankMediator(this);
        return true;
    }
    public boolean unregisterBank(Bank bank) {
        if (bankMap.containsKey(bank.bankCode)) {
            bankMap.remove(bank.bankCode);
            bank.setInterbankMediator(null);
            return true;
        }
        return false;
    }

    public boolean batchTransfer(String bankId) {
        if (pendingTransfers.containsKey(bankId)) {
            for (var transfer : pendingTransfers.get(bankId)) {
                bankMap.get(bankId).receiveExternalPayment(transfer);
            }
            pendingTransfers.remove(bankId);
            return true;
        }
        return false;
    }
}
